package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PawnTest {

    @Test
    public void whitePawnDoubleSpaceMovement(){
        ChessBoard board = ChessFactory.newStandardChessBoard();

        Piece pawn = board.getAt("B2");
        Collection<Position> positions = pawn.getPossiblePositions();
        assertEquals("White pawn didn't have 2 possible moves at the start of the game", 2, positions.size());

        Piece enemyPiece = new Bishop(board, Colour.BLACK);
        board.setAt("B4", enemyPiece);

        positions = pawn.getPossiblePositions();
        assertEquals("White pawn had more than one move when an enemy piece was 2 tiles in front.", 1, positions.size());


        Piece pawn2 = board.getAt("D2");
        Piece enemyPiece2 = new Rook(board, Colour.BLACK);
        board.setAt("D3", enemyPiece2);

        positions = pawn2.getPossiblePositions();
        assertTrue("White pawn was blocked, but had some moves.",  positions.isEmpty());

        Pawn newPawn = new Pawn(board, Colour.WHITE);
        board.setAt("C4", newPawn);
        assertEquals("Pawn was able to move 2 spaces, even though they weren't on their starting tile", 1, newPawn.getPossiblePositions().size());

    }

    @Test
    public void blackPawnDoubleSpaceMovement(){

    }

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
        assertTrue(possibleMoves.contains(new Position("D4")));

        Pawn enemyPawn = new Pawn(board, Colour.WHITE);
        board.setAt("E3", enemyPawn);

        possibleMoves = pawn.getPossiblePositions();
        assertEquals(possibleMoves.size(), 1);
    }

}