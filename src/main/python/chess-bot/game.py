from pieces import *
from util import *

class Board:
    def __init__(self, positions=None):
        self._grid = self._init_grid(positions)

    def _init_grid(self, positions):
        grid = {}
        for chess_notation in positions:
            pos = str_to_pos(chess_notation)
            name = positions[chess_notation]
            piece_cls, colour = piece_map[name]
            piece = piece_cls(self, colour)
            grid[pos] = piece
            piece.pos = pos
            
        return grid

    def get_piece(self, pos):
        return self._grid.get(pos)

    def pos_on_board(self, pos):
        if not pos:
            return False
        x, y = pos
        return 0 <= x < 8 and 0 <= y < 8

    def pos_is_empty(self, pos):
        return self.get_piece(pos) is None

    def set_at(self, pos, piece):
        if isinstance(pos, str):
            pos = str_to_pos(pos)
            
        self._grid[pos] = piece
        if piece:
            piece.pos = pos

    @property
    def pieces(self):
        return [piece for piece in self._grid.values() if piece]