#  Author:      Fred L. Drake, Jr.
#               fdrake@acm.org
#
#  This is a simple little module I wrote to make life easier.  I didn't
#  see anything quite like it in the library, though I may have overlooked
#  something.  I wrote this when I was trying to read some heavily nested
#  tuples with fairly non-descriptive content.  This is modeled very much
#  after Lisp/Scheme - style pretty-printing of lists.  If you find it
#  useful, thank small children who sleep at night.

"""Support to pretty-print lists, tuples, & dictionaries recursively.

Very simple, but useful, especially in debugging data structures.

Classes
-------

PrettyPrinter()
    Handle pretty-printing operations onto a stream using a configured
    set of formatting parameters.

Functions
---------

pformat()
    Format a Python object into a pretty-printed representation.

pprint()
    Pretty-print a Python object to a stream [default is sys.stdout].

saferepr()
    Generate a 'standard' repr()-like value, but protect against recursive
    data structures.

"""

import sys as _sys
from collections import OrderedDict as _OrderedDict
from io import StringIO as _StringIO

from typing import Any, Tuple, Dict, TextIO, cast, List

__all__ = ["pprint","pformat","isreadable","isrecursive","saferepr",
           "PrettyPrinter"]

# cache these for faster access:
_commajoin = ", ".join
_id = id
_len = len
_type = type


def pprint(object: object, stream: TextIO = None, indent: int = 1,
           width: int = 80, depth: int = None) -> None:
    """Pretty-print a Python object to a stream [default is sys.stdout]."""
    printer = PrettyPrinter(
        stream=stream, indent=indent, width=width, depth=depth)
    printer.pprint(object)

def pformat(object: object, indent: int = 1, width: int = 80,
            depth: int = None) -> str:
    """Format a Python object into a pretty-printed representation."""
    return PrettyPrinter(indent=indent, width=width, depth=depth).pformat(object)

def saferepr(object: object) -> str:
    """Version of repr() which can handle recursive data structures."""
    return _safe_repr(object, {}, None, 0)[0]

def isreadable(object: object) -> bool:
    """Determine if saferepr(object) is readable by eval()."""
    return _safe_repr(object, {}, None, 0)[1]

def isrecursive(object: object) -> bool:
    """Determine if object requires a recursive representation."""
    return _safe_repr(object, {}, None, 0)[2]

class _safe_key:
    """Helper function for key functions when sorting unorderable objects.

    The wrapped-object will fallback to an Py2.x style comparison for
    unorderable types (sorting first comparing the type name and then by
    the obj ids).  Does not work recursively, so dict.items() must have
    _safe_key applied to both the key and the value.

    """

    __slots__ = ['obj']

    def __init__(self, obj: Any) -> None:
        self.obj = obj

    def __lt__(self, other: Any) -> Any:
        rv = self.obj.__lt__(other.obj) # type: Any
        if rv is NotImplemented:
            rv = (str(type(self.obj)), id(self.obj)) < \
                 (str(type(other.obj)), id(other.obj))
        return rv

def _safe_tuple(t: Tuple[Any, Any]) -> Tuple[_safe_key, _safe_key]:
    "Helper function for comparing 2-tuples"
    return _safe_key(t[0]), _safe_key(t[1])

class PrettyPrinter:
    def __init__(self, indent: int = 1, width: int = 80, depth: int = None,
                 stream: TextIO = None) -> None:
        """Handle pretty printing operations onto a stream using a set of
        configured parameters.

        indent
            Number of spaces to indent for each level of nesting.

        width
            Attempted maximum number of columns in the output.

        depth
            The maximum depth to print out nested structures.

        stream
            The desired output stream.  If omitted (or false), the standard
            output stream available at construction will be used.

        """
        indent = int(indent)
        width = int(width)
        assert indent >= 0, "indent must be >= 0"
        assert depth is None or depth > 0, "depth must be > 0"
        assert width, "width must be != 0"
        self._depth = depth
        self._indent_per_level = indent
        self._width = width
        if stream is not None:
            self._stream = stream
        else:
            self._stream = _sys.stdout

    def pprint(self, object: object) -> None:
        self._format(object, self._stream, 0, 0, {}, 0)
        self._stream.write("\n")

    def pformat(self, object: object) -> str:
        sio = _StringIO()
        self._format(object, sio, 0, 0, {}, 0)
        return sio.getvalue()

    def isrecursive(self, object: object) -> int:
        return self.format(object, {}, 0, 0)[2]

    def isreadable(self, object: object) -> int:
        s, readable, recursive = self.format(object, {}, 0, 0)
        return readable and not recursive

    def _format(self, object: object, stream: TextIO, indent: int,
                allowance: int, context: Dict[int, int], level: int) -> None:
        level = level + 1
        objid = _id(object)
        if objid in context:
            stream.write(_recursion(object))
            self._recursive = True
            self._readable = False
            return
        rep = self._repr(object, context, level - 1)
        typ = _type(object)
        sepLines = _len(rep) > (self._width - 1 - indent - allowance)
        write = stream.write

        if self._depth and level > self._depth:
            write(rep)
            return

        if sepLines:
            r = getattr(typ, "__repr__", None)
            if isinstance(object, dict):
                write('{')
                if self._indent_per_level > 1:
                    write((self._indent_per_level - 1) * ' ')
                length = _len(object)
                if length:
                    context[objid] = 1
                    indent = indent + self._indent_per_level
                    if issubclass(typ, _OrderedDict):
                        items = list(object.items())
                    else:
                        items = sorted(object.items(), key=_safe_tuple)
                    key, ent = items[0]
                    rep = self._repr(key, context, level)
                    write(rep)
                    write(': ')
                    self._format(ent, stream, indent + _len(rep) + 2,
                                  allowance + 1, context, level)
                    if length > 1:
                        for key, ent in items[1:]:
                            rep = self._repr(key, context, level)
                            write(',\n%s%s: ' % (' '*indent, rep))
                            self._format(ent, stream, indent + _len(rep) + 2,
                                          allowance + 1, context, level)
                    indent = indent - self._indent_per_level
                    del context[objid]
                write('}')
                return

            if ((issubclass(typ, list) and r is list.__repr__) or
                (issubclass(typ, tuple) and r is tuple.__repr__) or
                (issubclass(typ, set) and r is set.__repr__) or
                (issubclass(typ, frozenset) and r is frozenset.__repr__)
               ):
                anyobj = cast(Any, object) # TODO Collection?
                length = _len(anyobj)
                if issubclass(typ, list):
                    write('[')
                    endchar = ']'
                    lst = anyobj
                elif issubclass(typ, set):
                    if not length:
                        write('set()')
                        return
                    write('{')
                    endchar = '}'
                    lst = sorted(anyobj, key=_safe_key)
                elif issubclass(typ, frozenset):
                    if not length:
                        write('frozenset()')
                        return
                    write('frozenset({')
                    endchar = '})'
                    lst = sorted(anyobj, key=_safe_key)
                    indent += 10
                else:
                    write('(')
                    endchar = ')'
                    lst = list(anyobj)
                if self._indent_per_level > 1:
                    write((self._indent_per_level - 1) * ' ')
                if length:
                    context[objid] = 1
                    indent = indent + self._indent_per_level
                    self._format(lst[0], stream, indent, allowance + 1,
                                 context, level)
                    if length > 1:
                        for ent in lst[1:]:
                            write(',\n' + ' '*indent)
                            self._format(ent, stream, indent,
                                          allowance + 1, context, level)
                    indent = indent - self._indent_per_level
                    del context[objid]
                if issubclass(typ, tuple) and length == 1:
                    write(',')
                write(endchar)
                return

        write(rep)

    def _repr(self, object: object, context: Dict[int, int],
              level: int) -> str:
        repr, readable, recursive = self.format(object, context.copy(),
                                                self._depth, level)
        if not readable:
            self._readable = False
        if recursive:
            self._recursive = True
        return repr

    def format(self, object: object, context: Dict[int, int],
               maxlevels: int, level: int) -> Tuple[str, int, int]:
        """Format object for a specific context, returning a string
        and flags indicating whether the representation is 'readable'
        and whether the object represents a recursive construct.
        """
        return _safe_repr(object, context, maxlevels, level)


# Return triple (repr_string, isreadable, isrecursive).

def _safe_repr(object: object, context: Dict[int, int],
               maxlevels: int, level: int) -> Tuple[str, bool, bool]:
    typ = _type(object)
    if typ is str:
        s = cast(str, object)
        if 'locale' not in _sys.modules:
            return repr(object), True, False
        if "'" in s and '"' not in s:
            closure = '"'
            quotes = {'"': '\\"'}
        else:
            closure = "'"
            quotes = {"'": "\\'"}
        qget = quotes.get
        sio = _StringIO()
        write = sio.write
        for char in s:
            if char.isalpha():
                write(char)
            else:
                write(qget(char, repr(char)[1:-1]))
        return ("%s%s%s" % (closure, sio.getvalue(), closure)), True, False

    r = getattr(typ, "__repr__", None)
    if issubclass(typ, dict) and r is dict.__repr__:
        if not object:
            return "{}", True, False
        objid = _id(object)
        if maxlevels and level >= maxlevels:
            return "{...}", False, objid in context
        if objid in context:
            return _recursion(object), False, True
        context[objid] = 1
        readable = True
        recursive = False
        components = []  # type: List[str]
        append = components.append
        level += 1
        saferepr = _safe_repr
        items = sorted((cast(dict, object)).items(), key=_safe_tuple)
        for k, v in items:
            krepr, kreadable, krecur = saferepr(k, context, maxlevels, level)
            vrepr, vreadable, vrecur = saferepr(v, context, maxlevels, level)
            append("%s: %s" % (krepr, vrepr))
            readable = readable and kreadable and vreadable
            if krecur or vrecur:
                recursive = True
        del context[objid]
        return "{%s}" % _commajoin(components), readable, recursive

    if (issubclass(typ, list) and r is list.__repr__) or \
       (issubclass(typ, tuple) and r is tuple.__repr__):
        anyobj = cast(Any, object) # TODO Sequence?
        if issubclass(typ, list):
            if not object:
                return "[]", True, False
            format = "[%s]"
        elif _len(anyobj) == 1:
            format = "(%s,)"
        else:
            if not object:
                return "()", True, False
            format = "(%s)"
        objid = _id(object)
        if maxlevels and level >= maxlevels:
            return format % "...", False, objid in context
        if objid in context:
            return _recursion(object), False, True
        context[objid] = 1
        readable = True
        recursive = False
        components = []
        append = components.append
        level += 1
        for o in anyobj:
            orepr, oreadable, orecur = _safe_repr(o, context, maxlevels, level)
            append(orepr)
            if not oreadable:
                readable = False
            if orecur:
                recursive = True
        del context[objid]
        return format % _commajoin(components), readable, recursive

    rep = repr(object)
    return rep, bool(rep and not rep.startswith('<')), False


def _recursion(object: object) -> str:
    return ("<Recursion on %s with id=%s>"
            % (_type(object).__name__, _id(object)))


def _perfcheck(object: object = None) -> None:
    import time
    if object is None:
        object = [("string", (1, 2), [3, 4], {5: 6, 7: 8})] * 100000
    p = PrettyPrinter()
    t1 = time.time()
    _safe_repr(object, {}, None, 0)
    t2 = time.time()
    p.pformat(object)
    t3 = time.time()
    print("_safe_repr:", t2 - t1)
    print("pformat:", t3 - t2)

if __name__ == "__main__":
    _perfcheck()
