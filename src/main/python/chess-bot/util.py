def new_pos(point, up=0, down=0, left=0, right=0):
    x, y = point
    return x + right -left, y - up + down
