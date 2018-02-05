class Board:
    def __init__(self):
        self._grid = self._init_grid()

    def _init_grid(self):
        grid = {}
        for x in range(8):
            for y in range(8):
                grid[(x,y)] = None
        return grid

    def get_piece(self, pos):
        return self._grid.get(pos)

    def pos_on_board(self, pos):
        x, y = pos
        return 0 < x < 8 and 0 < y < 8

    def pos_is_empty(self, pos):
        return self.get_piece(pos) is None

    def set_piece(self, piece, pos):
        self._grid[pos] = piece
        piece.pos = pos