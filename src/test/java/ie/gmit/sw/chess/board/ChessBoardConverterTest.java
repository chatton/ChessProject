package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Bishop;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.King;
import ie.gmit.sw.chess.board.pieces.Pawn;
import ie.gmit.sw.chess.board.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChessBoardConverterTest {
    @Test
    public void convertToDatabaseColumn() throws Exception {

        ChessBoard board = new ChessBoard();
        board.setAt("A5", new King(board, Colour.WHITE));
        board.setAt("B7", new Pawn(board, Colour.BLACK));
        board.setAt("D4", new Bishop(board, Colour.WHITE));

        ChessBoardConverter converter = new ChessBoardConverter();

        String result = converter.convertToDatabaseColumn(board);

        assertTrue(result.contains("K_A5"));
        assertTrue(result.contains("p_B7"));
        assertTrue(result.contains("B_D4"));
    }

    @Test
    public void convertToEntityAttribute() throws Exception {

        ChessBoardConverter converter = new ChessBoardConverter();
        ChessBoard board = converter.convertToEntityAttribute("A5_K,B7_p,D4_B");

        assertEquals(board.getPieces().size(), 3);
        Piece piece = board.getAt("A5");
        assertTrue(piece instanceof King);
        assertTrue(piece.getColour() == Colour.WHITE);

        piece = board.getAt("B5");
        assertTrue(piece instanceof Pawn);
        assertTrue(piece.getColour() == Colour.BLACK);

        piece = board.getAt("D4");
        assertTrue(piece instanceof Bishop);
        assertTrue(piece.getColour() == Colour.WHITE);
    }
}