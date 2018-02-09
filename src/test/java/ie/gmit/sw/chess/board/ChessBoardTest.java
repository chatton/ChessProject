package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.King;
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

}