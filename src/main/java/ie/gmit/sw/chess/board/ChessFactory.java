package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.*;

public class ChessFactory {

    /**
     * @return a conventional 8x8 chess board
     * with all the white pieces along the bottom
     * and black pieces at the top. Used whenever
     * a brand new chess board is required.
     */
    public static ChessBoard newStandardChessBoard() {
        ChessBoard board = new ChessBoard();
        board.setAt("A1", new Rook(board, Colour.WHITE));
        board.setAt("A2", new Pawn(board, Colour.WHITE));
        board.setAt("B1", new Knight(board, Colour.WHITE));
        board.setAt("B2", new Pawn(board, Colour.WHITE));
        board.setAt("C1", new Bishop(board, Colour.WHITE));
        board.setAt("C2", new Pawn(board, Colour.WHITE));
        board.setAt("D1", new Queen(board, Colour.WHITE));
        board.setAt("D2", new Pawn(board, Colour.WHITE));
        board.setAt("E1", new King(board, Colour.WHITE));
        board.setAt("E2", new Pawn(board, Colour.WHITE));
        board.setAt("F1", new Bishop(board, Colour.WHITE));
        board.setAt("F2", new Pawn(board, Colour.WHITE));
        board.setAt("G1", new Knight(board, Colour.WHITE));
        board.setAt("G2", new Pawn(board, Colour.WHITE));
        board.setAt("H1", new Rook(board, Colour.WHITE));
        board.setAt("H2", new Pawn(board, Colour.WHITE));

        board.setAt("A8", new Rook(board, Colour.BLACK));
        board.setAt("A7", new Pawn(board, Colour.BLACK));
        board.setAt("B8", new Knight(board, Colour.BLACK));
        board.setAt("B7", new Pawn(board, Colour.BLACK));
        board.setAt("C8", new Bishop(board, Colour.BLACK));
        board.setAt("C7", new Pawn(board, Colour.BLACK));
        board.setAt("D8", new Queen(board, Colour.BLACK));
        board.setAt("D7", new Pawn(board, Colour.BLACK));
        board.setAt("E8", new King(board, Colour.BLACK));
        board.setAt("E7", new Pawn(board, Colour.BLACK));
        board.setAt("F8", new Bishop(board, Colour.BLACK));
        board.setAt("F7", new Pawn(board, Colour.BLACK));
        board.setAt("G8", new Knight(board, Colour.BLACK));
        board.setAt("G7", new Pawn(board, Colour.BLACK));
        board.setAt("H8", new Rook(board, Colour.BLACK));
        board.setAt("H7", new Pawn(board, Colour.BLACK));

        return board;
    }
}
