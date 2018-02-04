from enum import Enum
from util import new_pos

class Colour(Enum):
    WHITE = "white"
    BLACK = "black"

class Pawn:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 100

    def _front_pos_valid(self, pos):
        return self.board.pos_on_board(pos) and self.board.pos_is_empty(pos)
            
    def _diagonal_valid(self, pos):
        if not self.board.pos_on_board(pos):
            return False

        if not self.board.pos_is_empty(pos):
            return False

        piece = self.board.get_piece(pos)
        return piece.colour != self.colour
        

    @property
    def moves(self):
        valid_positions = []
        pos_in_front = None
        diagonals = None
        if self.colour == Colour.WHITE:
            pos_in_front = new_pos(self.pos, up=1)
            diagonals = [
                new_pos(self.pos, up=1, right=1),
                new_pos(self.pos, up=1, left=1)
            ]
        else:
            pos_in_front = new_pos(self.pos, down=1)
            diagonals = [
                new_pos(self.pos, down=1, right=1),
                new_pos(self.pos, down=1, left=1)
            ]

        if self._front_pos_valid(pos_in_front):
            valid_positions.append(pos_in_front)

        valid_positions.extend([pos for pos in diagonals if self._diagonal_valid(pos)])
        return valid_positions
        

class Rook:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 500
    
    @property
    def moves(self):
        valid_positions = []

        x, y = self.pos
        i = x + 1
        while i < 8:
            pos = i, y
            i += 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)
            break

        i = x - 1
        while i >= 0:
            pos = i, y
            i -= 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)
            break

        j = y - 1
        while j >= 0:
            pos = x, j
            j -= 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)
            break

        j = y + 1
        while j < 8:
            pos = x, j
            j += 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)
            break

        return valid_positions


class Knight:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.value = 320
        self.pos = None

    def _valid_pos(self, pos):
        if not self.board.pos_on_board(pos):
            return False
        if self.board.pos_is_empty(pos):
            return True
        piece = self.board.get_piece(pos)
        return piece.colour != self.colour

    @property
    def moves(self):
        positions = [
            new_pos(self.pos, up=2, right=1),
            new_pos(self.pos, up=1, right=2),
            new_pos(self.pos, up=2, left=1),
            new_pos(self.pos, up=1, left=2),
            new_pos(self.pos, down=2, right=1),
            new_pos(self.pos, down=1, right=2),
            new_pos(self.pos, down=2, left=1),
            new_pos(self.pos, down=1, left=2)
        ]
        return [pos for pos in positions if self._valid_pos(pos)]


class King:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 20000

    def _valid_pos(self, pos):
        if not self.board.pos_on_board(pos):
            return False
        if self.board.pos_is_empty(pos):
            return True
        piece = self.board.get_piece(pos)
        return piece.colour != self.colour

    @property
    def moves(self):
        positions = [
            new_pos(self.pos, up=1),
            new_pos(self.pos, down=1),
            new_pos(self.pos, right=1),
            new_pos(self.pos, left=1),
            new_pos(self.pos, down=1, right=1),
            new_pos(self.pos, down=1, left=1),
            new_pos(self.pos, up=1, left=1),
            new_pos(self.pos, up=1, right=1)
        ]

        return [pos for pos in positions if self._valid_pos(pos)]

class Queen:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 900

    @property
    def moves(self):
        rook = Rook(self.board, self.colour)
        rook.pos = self.pos
        bishop = Bishop(self.board, self.colour)
        bishop.pos = self.pos
        return bishop.moves + rook.moves


class Bishop:
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 320

    @property
    def moves(self):
        valid_positions = []

        row, col = self.pos

        # down and right
        while row < 7 and col < 7:
            row += 1
            col += 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)
            break

        row, col = self.pos
        # down and left
        while row > 0 and col < 7:
            row -= 1
            col += 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)

            break

        row, col = self.pos
        # up and left
        while row > 0 and col >= 0:
            row -= 1
            col -= 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)

            break

        row, col = self.pos
        # up and right
        while row < 7 and col > 0:
            row += 1
            col -= 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(pos)
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(pos)

            break

        return valid_positions
