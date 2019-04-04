"""Defines experimental extensions to the standard "typing" module that are
supported by the mypy typechecker.

Example usage:
    from mypy_extensions import TypedDict
"""

# NOTE: This module must support Python 2.7 in addition to Python 3.x

import sys
# _type_check is NOT a part of public typing API, it is used here only to mimic
# the (convenient) behavior of types provided by typing module.
from typing import _type_check  # type: ignore


def _check_fails(cls, other):
    try:
        if sys._getframe(1).f_globals['__name__'] not in ['abc', 'functools', 'typing']:
            # Typed dicts are only for static structural subtyping.
            raise TypeError('TypedDict does not support instance and class checks')
    except (AttributeError, ValueError):
        pass
    return False


def _dict_new(cls, *args, **kwargs):
    return dict(*args, **kwargs)


def _typeddict_new(cls, _typename, _fields=None, **kwargs):
    if _fields is None:
        _fields = kwargs
    elif kwargs:
        raise TypeError("TypedDict takes either a dict or keyword arguments,"
                        " but not both")
    return _TypedDictMeta(_typename, (), {'__annotations__': dict(_fields)})


class _TypedDictMeta(type):
    def __new__(cls, name, bases, ns):
        # Create new typed dict class object.
        # This method is called directly when TypedDict is subclassed,
        # or via _typeddict_new when TypedDict is instantiated. This way
        # TypedDict supports all three syntaxes described in its docstring.
        # Subclasses and instanes of TypedDict return actual dictionaries
        # via _dict_new.
        ns['__new__'] = _typeddict_new if name == 'TypedDict' else _dict_new
        tp_dict = super(_TypedDictMeta, cls).__new__(cls, name, (dict,), ns)
        try:
            # Setting correct module is necessary to make typed dict classes pickleable.
            tp_dict.__module__ = sys._getframe(2).f_globals.get('__name__', '__main__')
        except (AttributeError, ValueError):
            pass
        anns = ns.get('__annotations__', {})
        msg = "TypedDict('Name', {f0: t0, f1: t1, ...}); each t must be a type"
        anns = {n: _type_check(tp, msg) for n, tp in anns.items()}
        for base in bases:
            anns.update(base.__dict__.get('__annotations__', {}))
        tp_dict.__annotations__ = anns
        return tp_dict

    __instancecheck__ = __subclasscheck__ = _check_fails


TypedDict = _TypedDictMeta('TypedDict', (dict,), {})
TypedDict.__module__ = __name__
TypedDict.__doc__ = \
    """A simple typed name space. At runtime it is equivalent to a plain dict.

    TypedDict creates a dictionary type that expects all of its
    instances to have a certain set of keys, with each key
    associated with a value of a consistent type. This expectation
    is not checked at runtime but is only enforced by typecheckers.
    Usage::

        Point2D = TypedDict('Point2D', {'x': int, 'y': int, 'label': str})
        a: Point2D = {'x': 1, 'y': 2, 'label': 'good'}  # OK
        b: Point2D = {'z': 3, 'label': 'bad'}           # Fails type check
        assert Point2D(x=1, y=2, label='first') == dict(x=1, y=2, label='first')

    The type info could be accessed via Point2D.__annotations__. TypedDict
    supports two additional equivalent forms::

        Point2D = TypedDict('Point2D', x=int, y=int, label=str)

        class Point2D(TypedDict):
            x: int
            y: int
            label: str

    The latter syntax is only supported in Python 3.6+, while two other
    syntax forms work for Python 2.7 and 3.2+
    """


# Return type that indicates a function does not return
class NoReturn: pass
