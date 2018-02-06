from enum import Enum
from util import new_pos, pos_to_str

class Colour(Enum):
    WHITE = "white"
    BLACK = "black"

class Move:
    def __init__(self, origin, dest):
        self.origin = pos_to_str(origin)
        self.dest = pos_to_str(dest)

class Piece:

    def __init__(self):
        self.name = "Piece"
        self.pos = -1,-1
        self.colour = Colour.BLACK

    @property
    def moves(self):
        return []

    def __eq__(self, other):
        return (
            other.pos == self.pos 
            and other.name == self.name 
            and other.colour == self.colour
        )

    def __iter__(self):
        return iter(self.moves)

    def __hash__(self):
        x,y = self.pos
        return hash(self.colour) + x ^ y

    def __str__(self):
        return (
            str(self.colour).split(".")[1].capitalize() + " "  + 
            self.name + " at " + pos_to_str(self.pos) + " - legal moves: " +
            str(list(map(pos_to_str, self.moves)))
        )

class Pawn(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 100
        self.name = "Pawn"

    def _front_pos_valid(self, pos):
        return self.board.pos_on_board(pos) and self.board.pos_is_empty(pos)
            
    def _diagonal_valid(self, pos):
        if not self.board.pos_on_board(pos):
            return False

        if self.board.pos_is_empty(pos):
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
            valid_positions.append(Move(self.pos, pos_in_front))

        valid_positions.extend([Move(self.pos, pos) for pos in diagonals if self._diagonal_valid(pos)])
        return valid_positions
        

class Rook(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 500
        self.name = "Rook"
    
    @property
    def moves(self):
        valid_positions = []

        x, y = self.pos
        i = x + 1
        while i < 8:
            pos = i, y
            i += 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))
            break

        i = x - 1
        while i >= 0:
            pos = i, y
            i -= 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))
            break

        j = y - 1
        while j >= 0:
            pos = x, j
            j -= 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))
            break

        j = y + 1
        while j < 8:
            pos = x, j
            j += 1
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))
            break

        return valid_positions


class Knight(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.value = 320
        self.pos = None
        self.name = "Knight"

    def _valid_pos(self, pos):
        if not pos:
            return False
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
        return [Move(self.pos, pos) for pos in positions if self._valid_pos(pos)]


class King(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 20000
        self.name = "King"

    def _valid_pos(self, pos):
        if not pos:
            return False
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

        return [Move(self.pos, pos) for pos in positions if self._valid_pos(pos)]

class Queen(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 900
        self.name = "Queen"

    @property
    def moves(self):
        rook = Rook(self.board, self.colour)
        rook.pos = self.pos
        bishop = Bishop(self.board, self.colour)
        bishop.pos = self.pos
        return bishop.moves + rook.moves


class Bishop(Piece):
    def __init__(self, board, colour):
        self.board = board
        self.colour = colour
        self.pos = None
        self.value = 320
        self.name = "Bishop"

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
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))
            break

        row, col = self.pos
        # down and left
        while row > 0 and col < 7:
            row -= 1
            col += 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))

            break

        row, col = self.pos
        # up and left
        while row > 0 and col >= 0:
            row -= 1
            col -= 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))

            break

        row, col = self.pos
        # up and right
        while row < 7 and col > 0:
            row += 1
            col -= 1
            pos = (row, col)
            if self.board.pos_is_empty(pos):
                valid_positions.append(Move(self.pos, pos))
                continue

            piece = self.board.get_piece(pos)
            if piece.colour != self.colour:
                valid_positions.append(Move(self.pos, pos))

            break

        return valid_positions

# a mapping of the piece names as given in the response to which 
# python classes and colours they correspond to.

piece_map = {
    "wPawn" : (Pawn, Colour.WHITE),
    "wRook" : (Rook, Colour.WHITE),
    "wBishop" : (Bishop, Colour.WHITE),
    "wKing" : (King, Colour.WHITE),
    "wQueen" : (Queen, Colour.WHITE),
    "wKnight" : (Knight, Colour.WHITE),
    "bPawn" : (Pawn, Colour.BLACK),
    "bRook" : (Rook, Colour.BLACK),
    "bBishop" : (Bishop, Colour.BLACK),
    "bKing" : (King, Colour.BLACK),
    "bQueen" : (Queen, Colour.BLACK),
    "bKnight" : (Knight, Colour.BLACK)
}