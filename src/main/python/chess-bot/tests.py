from game import *
from pieces import *

import unittest

def init_board():
    return Board({
        "G1": "wKnight",
        "G2": "wPawn",
        "E1": "wQueen",
        "E2": "wPawn",
        "C1": "wBishop",
        "C2": "wPawn",
        "A1": "wRook",
        "G7": "bPawn",
        "A2": "wPawn",
        "G8": "bKnight",
        "E7": "bPawn",
        "E8": "bQueen",
        "C7": "bPawn",
        "C8": "bBishop",
        "A7": "bPawn",
        "A8": "bRook",
        "H1": "wRook",
        "H2": "wPawn",
        "F1": "wBishop",
        "F2": "wPawn",
        "D1": "wKing",
        "D2": "wPawn",
        "B1": "wKnight",
        "H7": "bPawn",
        "B2": "wPawn",
        "H8": "bRook",
        "F7": "bPawn",
        "F8": "bBishop",
        "D7": "bPawn",
        "D8": "bKing",
        "B7": "bPawn",
        "B8": "bKnight"
        })

class BoardTest(unittest.TestCase):

    def test_get_piece(self):
        board = init_board()
        self.assertEqual(board.get_piece("B8").name, "Knight")
        self.assertEqual(board.get_piece("D7").name, "Pawn")
        self.assertEqual(board.get_piece("C3"), None)

    def test_make_move(self):
        board = init_board()
        pawn = board.get_piece("B7")
        board.make_move(Move("B7", "B6"))
        
        self.assertTrue(board.pos_is_empty("B7"))
        self.assertEqual(board.get_piece("B6"), pawn)

        knight = board.get_piece("B1")
        board.make_move(Move("B1", "C3"))
        self.assertTrue(board.pos_is_empty("B1"))
        self.assertEqual(board.get_piece("C3"), knight)


    def test_undo_move(self):    
        board = init_board()
        pawn = board.get_piece("B7")
        board.make_move(Move("B7", "B6"))
        knight = board.get_piece("B1")
        board.make_move(Move("B1", "C3"))

        board.undo_move()
        board.undo_move()

        self.assertTrue(board.pos_is_empty("B6"))
        self.assertEqual(board.get_piece("B7"), pawn)
        self.assertTrue(board.pos_is_empty("C3"))
        self.assertEqual(board.get_piece("B1"), knight)

class PieceTest(unittest.TestCase):

    def test_bishop_moves(self):
        board = init_board()
        bishop = board.get_piece("C8")
        print(bishop.moves)
        self.assertEqual(len(bishop.moves), 0)

        bishop = Bishop(board, Colour.WHITE)
        board.set_at("H4", bishop)
        self.assertEqual(len(bishop.moves), 4)
        
if __name__ == "__main__":
    unittest.main()