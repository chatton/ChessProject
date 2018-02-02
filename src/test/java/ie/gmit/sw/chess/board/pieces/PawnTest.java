package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PawnTest {

    @Test
    public void testWhitePawnGetPossiblePositions() {

        ChessBoard board = new ChessBoard(8);
        Pawn pawn = new Pawn(board, Colour.WHITE);
        board.setAt(new Position(3, 3), pawn);

        List<Position> possibleMoves = pawn.getPossiblePositions();
        assertEquals("Pawn had 3 empty spaces in front of it and didn't have just 1 position.", possibleMoves.size(), 1);
        assertTrue(possibleMoves.contains(new Position(3, 2)));

        Pawn enemyPawn = new Pawn(board, Colour.BLACK);
        board.setAt(new Position(4, 2), enemyPawn);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 2);

        Piece allyKing = new King(board, Colour.WHITE);
        board.setAt(new Position(2, 2), allyKing);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 2);
    }

}