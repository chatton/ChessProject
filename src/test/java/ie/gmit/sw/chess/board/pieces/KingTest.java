package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class KingTest {


    @Test
    public void testKingPossiblePositions(){
        ChessBoard board = new ChessBoard(8,8);
        King king = new King(board, new Position(2,2), Colour.BLACK);
        Piece whiteRook = new Rook(board, new Position(2,3), Colour.WHITE);
        Piece blackRook = new Rook(board, new Position(3,2), Colour.BLACK);
        board.setAt(new Position(2,2), king);
        board.setAt(new Position(2,3), whiteRook);
        board.setAt(new Position(3,2), blackRook);

        List<Position> positions = king.getPossiblePositions();

        assertTrue(!positions.contains(new Position(2,2))); // the current position is not a possible option.

        assertTrue(positions.size() == 7); // 7 as 6 are empty, 1 is an enemy and 1 is allied.

        assertTrue(positions.contains(new Position(2,3))); // the position of an opposing colour's piece should be a possible position

        assertFalse(positions.contains(new Position(3,2))); // the position of an allied piece should not be in the list.

    }
}