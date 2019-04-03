function f(int[] xs) -> (int[] ys)
requires |xs| > 0
ensures ys[0] == 0:
    //
    xs[0] = 0
    //
    assert some { k in 0..|xs| | xs[k] == 0 }
    //
    return xs

//
public export method test():
    int[] xs = f([1])
    //
    assert xs[0] == 0
