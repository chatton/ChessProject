import random

def new_pos(pos, up=0, down=0, left=0, right=0):
    """helper method to generate positions relative distances from an oritin position"""
    if not pos:
        return None
    x, y = pos
    return x + right -left, y - up + down

def str_to_pos(chess_notation):
    """converts from chess notation to co-ordinates, e.g. A1 -> 0, 7"""
    letter = chess_notation[0]
    x = ord(letter) - ord("A")
    y = 8 - int(chess_notation[1])
    return x, y

def pos_to_str(pos):
    """converts from grid co-ords to string. 0,7 -> A1"""
    x,y = pos
    letter = chr(ord("A") + x)
    y = 8 - y
    return letter + str(y)

def random_choice(seq):
    return None if not seq else random.choice(seq)

