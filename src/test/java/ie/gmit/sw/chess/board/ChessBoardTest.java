package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Bishop;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.King;
import ie.gmit.sw.chess.board.pieces.Knight;
import ie.gmit.sw.chess.board.pieces.Pawn;
import ie.gmit.sw.chess.board.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void testUndoSingleMove(){
        ChessBoard board = new ChessBoard(8);
        Piece pawn = new Pawn(board, Colour.WHITE);
        board.setAt("A2", pawn);
        board.makeMove(new Move("A2", "A3"));
        assertEquals(pawn,  board.getAt("A3"));
        assertEquals(null, board.getAt("A2"));
        board.undoLastMove();
        assertTrue(board.getAt("A2").equals(pawn));

        Piece knight = new Knight(board, Colour.BLACK);

        board.setAt("B3", knight);
        board.makeMove(new Move("A2", "B3"));
        assertTrue("Pawn moved to capture knight but was not occupying the position the knight was at.",board.getAt("B3").equals(pawn));

        board.undoLastMove();

        assertEquals(knight, board.getAt("B3"));
        assertEquals(pawn, board.getAt("A2"));
    }

    @Test
    public void testUndoMultipleMoves(){
        ChessBoard board = ChessFactory.newStandardChessBoard();

        Piece wPawn = board.getAt("B2");
        Piece bPawn = board.getAt("C7");

        // make 4 normal moves
        wPawn.moveTo("B3");
        bPawn.moveTo("C6");
        wPawn.moveTo("B4");
        bPawn.moveTo("C5");

        Piece pieceToCapture = new Bishop(board, Colour.BLACK);
        board.setAt("A5", pieceToCapture);
        // make one more move that's a capture
        wPawn.moveTo("A5");

        // undo all 5 moves
        for(int i = 0; i < 5; i++){
            board.undoLastMove();
        }

        // back to original board state.
        assertEquals(wPawn, board.getAt("B2"));
        assertEquals(bPawn, board.getAt("C7"));
        assertEquals(pieceToCapture, board.getAt("A5"));

    }

}