import random


def new_pos(pos, up=0, down=0, left=0, right=0):
    """helper method to generate positions relative distances from an origin position
    >>> new_pos((0,0), down=1)
    (0, 1)
    >>> new_pos((2,3), up=2, left=1)
    (1, 1)
    """
    if not pos:
        return None
    x, y = pos
    return x + right - left, y - up + down


def str_to_pos(chess_notation):
    """converts from chess notation to co-ordinates
    >>> str_to_pos("A3")
    (0, 5)
    >>> str_to_pos("B6")
    (1, 2)
    """
    letter = chess_notation[0]
    x = ord(letter) - ord("A")
    y = 8 - int(chess_notation[1])
    return x, y


def pos_to_str(pos):
    """
    >>> pos_to_str((0,0))
    'A8'
    >>> pos_to_str((3,4))
    'D4'
    """
    x, y = pos
    letter = chr(ord("A") + x)
    y = 8 - y
    return letter + str(y)


def random_choice(seq):
    """wrapper around random.choice that returns None if the sequence is empty"""
    return None if not seq else random.choice(seq)


def _test():
    import doctest

    doctest.testmod()


if __name__ == "__main__":
    _test()
