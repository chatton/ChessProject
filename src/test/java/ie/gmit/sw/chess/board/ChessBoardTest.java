package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Bishop;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.King;
import ie.gmit.sw.chess.board.pieces.Knight;
import ie.gmit.sw.chess.board.pieces.Pawn;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.chess.board.pieces.Queen;
import ie.gmit.sw.chess.board.pieces.Rook;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChessBoardTest {


    @Test
    public void isCheck() throws Exception {

        ChessBoard board = ChessFactory.newStandardChessBoard();
        assertFalse(board.isCheck(Colour.WHITE));
        assertFalse(board.isCheck(Colour.BLACK));

        board.emptyPosition("D1");

        King king = new King(board, Colour.WHITE);
        board.setAt("F6", king);
        assertTrue(board.isCheck(Colour.WHITE));

        // take the king off the board
        board.emptyPosition(king.getPosition());

        king = new King(board, Colour.WHITE);
        board.setAt("A4", king);

        board.emptyPosition("A7");
        assertTrue(board.isCheck(Colour.WHITE));

        assertFalse(board.isCheck(Colour.BLACK));

    }

    @Test
    public void testUndoSingleMove() {
        ChessBoard board = new ChessBoard();
        Piece pawn = new Pawn(board, Colour.WHITE);
        board.setAt("A2", pawn);
        board.makeMove(new Move("A2", "A3"));
        assertEquals(pawn, board.getAt("A3"));
        assertEquals(null, board.getAt("A2"));
        board.undoLastMove();
        assertTrue(board.getAt("A2").equals(pawn));

        Piece knight = new Knight(board, Colour.BLACK);

        board.setAt("B3", knight);
        board.makeMove(new Move("A2", "B3"));
        assertTrue("Pawn moved to capture knight but was not occupying the position the knight was at.", board.getAt("B3").equals(pawn));

        board.undoLastMove();

        assertEquals(knight, board.getAt("B3"));
        assertEquals(pawn, board.getAt("A2"));
    }

    @Test
    public void testUndoMultipleMoves() {
        ChessBoard board = ChessFactory.newStandardChessBoard();

        Piece wPawn = board.getAt("B2");
        Piece bPawn = board.getAt("C7");

        // make 4 normal moves
        board.makeMove(new Move("B2", "B3"));
        board.makeMove(new Move("C7", "C6"));
        board.makeMove(new Move("B3", "B4"));
        board.makeMove(new Move("C6", "C5"));


        Piece pieceToCapture = new Bishop(board, Colour.BLACK);
        board.setAt("A5", pieceToCapture);
        // make one more move that's a capture
        board.makeMove(new Move("B4", "A5"));

        // undo all 5 moves
        for (int i = 0; i < 5; i++) {
            board.undoLastMove();
        }

        // back to original board state.
        assertEquals(wPawn, board.getAt("B2"));
        assertEquals(bPawn, board.getAt("C7"));
        assertEquals(pieceToCapture, board.getAt("A5"));

    }

    @Test
    public void testGettingPossiblePositionsDoesntGivePositionsThatWouldResultInCheck() {
        ChessBoard board = new ChessBoard();

        Queen bQueen = new Queen(board, Colour.BLACK);
        Knight wKnight = new Knight(board, Colour.WHITE);
        King wKing = new King(board, Colour.WHITE);

        board.setAt("C8", bQueen);
        board.setAt("C4", wKnight);
        board.setAt("C2", wKing);

        assertFalse(board.moveIsValid(new Move("C4", "E5"))); // would result in check

        Knight wKnight2 = new Knight(board, Colour.WHITE);
        board.setAt("E7", wKnight2);
        board.makeMove(new Move("E7", "C8"));// captures the queen

//        board.emptyPosition("C8"); // remove the queen.
        assertTrue(board.moveIsValid(new Move("C4", "E5"))); // won't result in check anymore
    }

    @Test
    public void testCheckMate(){
        ChessBoard board = new ChessBoard();
        board.setAt("A8", new King(board, Colour.BLACK));
        board.setAt("B7", new Queen(board, Colour.WHITE));
        board.setAt("C6", new Bishop(board, Colour.WHITE));
        board.setAt("D1", new King(board, Colour.WHITE));
        board.setAt("F7", new Rook(board, Colour.BLACK));

        assertFalse(board.isCheckMate(Colour.BLACK));

        board = new ChessBoard();
        board.setAt("A8", new King(board, Colour.BLACK));
        board.setAt("B7", new Queen(board, Colour.WHITE));
        board.setAt("C6", new Bishop(board, Colour.WHITE));
        board.setAt("D1", new King(board, Colour.WHITE));
        board.setAt("F5", new Rook(board, Colour.BLACK));

        assertTrue(board.isCheckMate(Colour.BLACK));
        assertFalse(board.isCheckMate(Colour.WHITE));
    }
}