from pieces import *
from util import *
import copy


def copy_of(board):
    internals = copy.deepcopy(board._grid)
    new_board = Board({})
    new_board._grid = internals
    return new_board


class Board:
    def __init__(self, positions):
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

        raise ValueError("No [{}] king was found.".format(colour))

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

    def all_possible_moves(self, colour):
        all_pieces = [piece for piece in self.pieces if piece.colour == colour]
        all_moves = []
        for piece in all_pieces:
            all_moves.extend(piece.moves)
        return all_moves

    def all_board_states(self, current_turn_colour, value=0):
        all_moves = self.all_possible_moves(current_turn_colour)
        board_states = []
        for move in all_moves:
            target = self.get_piece(move.dest)
            if target:
                value += target.value
            board_states.append(BoardState(self, move, value))
        return board_states


class BoardState:
    def __init__(self, board, move, value):
        self.move = move
        self.value = value
        self.board = copy_of(board)  # copy the board so as not to mutate it
        self.board.make_move(move)  # apply the desired move so only the new board is altered.

    def is_check(self, colour):
        return self.board.is_check(colour)

    def all_board_states(self, colour):
        return self.board.all_board_states(colour)
