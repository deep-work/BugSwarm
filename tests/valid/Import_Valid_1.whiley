

function f(int x) -> int:
    if x < 0:
        return 0
    else:
        return x

public export method test() -> void:
    assume f(1) == 1
