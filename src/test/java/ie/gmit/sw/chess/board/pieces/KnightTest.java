package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KnightTest{

    @Test
    public void testKnightPossiblePositions() {

        // create a populated board.
        ChessBoard board = ChessFactory.newStandardChessBoard();
        Knight blackKnight = (Knight) board.getAt("B8"); // grab the black knight
        Collection<Position> positions = blackKnight.getPossiblePositions();
        // should have 2 possible moves
        assertTrue("Knight had possible moves but was partially surrounded.", positions.size() == 2);

        // add a new white Knight
        Knight whiteKnight = new Knight(board, Colour.WHITE);
        board.setAt("D4", whiteKnight);
        positions = whiteKnight.getPossiblePositions();

        // check that the Knight has all the correct positions available.
        assertEquals(6, positions.size());

        assertTrue(positions.contains(new Position("F5")));
        assertTrue(positions.contains(new Position("E6")));
        assertTrue(positions.contains(new Position("F3")));
        assertTrue(positions.contains(new Position("B3")));
        assertTrue(positions.contains(new Position("B5")));
        assertTrue(positions.contains(new Position("C6")));

        board.makeMove(new Move("D4", "E6"));
        board.makeMove(new Move("E6", "D1"));//capture bQueen

        positions = whiteKnight.getPossiblePositions();
        // check possible positions from the new location(D1), can capture 2 and move to 2
        assertEquals(4, positions.size());

        // use an empty board.
        board = new ChessBoard(8);
        whiteKnight = new Knight(board, Colour.WHITE);
        // placing the bishop on C6
        board.setAt("C6", whiteKnight);

        // should have 7 possible moves from this board state.
        positions = whiteKnight.getPossiblePositions();
        assertEquals(8, positions.size());
    }

}
