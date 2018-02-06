package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RookTest {

    private ChessBoard board;

    @Before
    public void initBoard() {
        board = ChessFactory.newStandardChessBoard();
    }

    @Test
    public void testRookMovement() {
        Rook rook = new Rook(board, Colour.WHITE);
        board.setAt("A6", rook);
        List<Position> positions = rook.getPossiblePositions();
        assertEquals(11, positions.size());

        board.makeMove(new Move("A6", "E6"));

        positions = rook.getPossiblePositions();
        assertEquals(11, positions.size());

        // empty the piece at E7
        board.setAt("E7", null);

        //should be one more free piece since there
        // is now an empty slot.
        positions = rook.getPossiblePositions();
        assertEquals(12, positions.size());

        initBoard();
        Piece piece = board.getAt("A1");
        positions = piece.getPossiblePositions();
        assertTrue(positions.isEmpty()); // should have no moves because it's blocked by other white pieces.
    }

}