from pieces import *
from util import *

class Board:
    def __init__(self, positions=None):
        self._grid = self._init_grid(positions)
        self._move_history = []

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


    def is_check(self, colour):
        all_enemy_pieces = [piece for piece in self.pieces if piece.colour != colour]
        danger_zone = []
        
        for piece in all_enemy_pieces:
            danger_zone.extend([move.dest for move in piece.moves])

        king = self.get_king(colour)

        return pos_to_str(king.pos) in danger_zone
        
    def make_move(self, move):
        piece = self.get_piece(move.origin)
        move.dest_piece = self.get_piece(move.dest)
        move.origin_piece = piece
        self.set_at(move.dest, piece)
        self.set_at(move.origin, None)
        self._move_history.append(move)
       

    def undo_move(self):
        move = self._move_history.pop()
        reversed_move = Move(move.dest, move.origin)
        reversed_move.dest_piece = move.origin_piece
        reversed_move.origin_piece = move.dest_piece
        self.set_at(reversed_move.dest, reversed_move.dest_piece)
        self.set_at(reversed_move.origin, reversed_move.origin_piece)

    def get_king(self, colour):
        for piece in self.pieces:
            if piece.colour == colour and isinstance(piece, King):
                return piece

    def get_piece(self, pos):
        if isinstance(pos, str):
            pos = str_to_pos(pos)

        return self._grid.get(pos)

    def pos_on_board(self, pos):
        if not pos:
            return False
        if isinstance(pos, str):
            pos = str_to_pos(pos)
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
