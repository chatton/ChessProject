package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PawnTest {

    @Test
    public void testWhitePawnGetPossiblePositions() {

        ChessBoard board = new ChessBoard();
        Pawn pawn = new Pawn(board, Colour.WHITE);
        board.setAt("D5", pawn);

        Collection<Position> possibleMoves = pawn.getPossiblePositions();
        assertEquals("Pawn had 3 empty spaces in front of it and didn't have just 1 position.", possibleMoves.size(), 1);
        assertTrue(possibleMoves.contains(new Position(3, 2)));

        Pawn enemyPawn = new Pawn(board, Colour.BLACK);
        board.setAt("E7", enemyPawn);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 1);

        Piece allyKing = new King(board, Colour.WHITE);
        board.setAt(new Position(2, 2), allyKing);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 1);
    }

    @Test
    public void testBlackPawnGetPossiblePositions() {

        ChessBoard board = new ChessBoard();
        Pawn pawn = new Pawn(board, Colour.BLACK);
        board.setAt("D5", pawn);

        Collection<Position> possibleMoves = pawn.getPossiblePositions();
        assertEquals("Pawn had 3 empty spaces in front of it and didn't have just 1 position.", possibleMoves.size(), 1);
        assertTrue(possibleMoves.contains(new Position("D5")));

        Pawn enemyPawn = new Pawn(board, Colour.WHITE);
        board.setAt("D6", enemyPawn);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 2);

        Piece allyKing = new King(board, Colour.BLACK);
        board.setAt(new Position("C6"), allyKing);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 2);
    }

}