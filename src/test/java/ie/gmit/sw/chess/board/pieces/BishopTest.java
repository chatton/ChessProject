package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BishopTest {


    @Test
    public void testBishopPossiblePositions() {
        // create a populated board.
        ChessBoard board = ChessFactory.newStandardChessBoard();
        Bishop blackBishop = (Bishop) board.getAt("C8"); // grab the black bishop
        List<Position> positions = blackBishop.getPossiblePositions();
        // should not have any possible moves
        assertTrue("Bishop had possible moves but was surrounded.", positions.size() == 0);

        // add a new white bishop
        Bishop whiteBishop = new Bishop(board, Colour.WHITE);
        board.setAt("H5", whiteBishop);
        positions = whiteBishop.getPossiblePositions();

        // check that the bishop has all the correct positions available.
        assertEquals(4, positions.size());

        assertTrue(positions.contains(new Position("F7")));
        assertTrue(positions.contains(new Position("F3")));
        assertTrue(positions.contains(new Position("G6")));
        assertTrue(positions.contains(new Position("G4")));

        board.makeMove(new Move("H5", "F7")); // make a legal move to capture the black pawn

        positions = whiteBishop.getPossiblePositions();
        // check possible positions from the new location
        assertEquals(8, positions.size());

        // use an empty board.
        board = new ChessBoard(8);
        whiteBishop = new Bishop(board, Colour.WHITE);
        // placing the bishop on H5
        board.setAt("H5", whiteBishop);

        // should have 7 possible moves from this board state.
        positions = whiteBishop.getPossiblePositions();
        assertEquals(7, positions.size());


    }
}
