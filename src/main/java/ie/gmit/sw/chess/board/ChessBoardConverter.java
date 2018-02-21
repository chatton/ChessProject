package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.*;
import ie.gmit.sw.utilities.Util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collection;


@Converter
public class ChessBoardConverter implements AttributeConverter<ChessBoard, String> {

    @Override
    public String convertToDatabaseColumn(ChessBoard board) {

        StringBuilder sb = new StringBuilder();
        Collection<Piece> allPieces = board.getPieces();
        for (Piece piece : allPieces) {
            sb.append(shrink(piece))
                    .append("_")
                    .append(Util.positionToString(piece.getPosition()))
                    .append(",");

        }
        return sb.toString();
    }

    private String shrink(Piece piece) {
        switch (piece.getName()) {
            case "wPawn":
                return "P";
            case "wBishop":
                return "B";
            case "wRook":
                return "R";
            case "wKnight":
                return "N";
            case "wKing":
                return "K";
            case "wQueen":
                return "Q";

            case "bPawn":
                return "p";
            case "bBishop":
                return "b";
            case "bRook":
                return "r";
            case "bKnight":
                return "n";
            case "bKing":
                return "k";
            case "bQueen":
                return "q";


        }
        throw new IllegalArgumentException("Unrecognised piece");
    }

    private Piece expand(String shortName, ChessBoard board){
        switch(shortName){
            case "P":
                return new Pawn(board, Colour.WHITE);
            case "B":
                return new Bishop(board, Colour.WHITE);
            case "R":
                return new Rook(board, Colour.WHITE);
            case "N":
                return new Knight(board, Colour.WHITE);
            case "K":
                return new King(board, Colour.WHITE);
            case "Q":
                return new Queen(board, Colour.WHITE);

            case "p":
                return new Pawn(board, Colour.BLACK);
            case "b":
                return new Bishop(board, Colour.BLACK);
            case "r":
                return new Rook(board, Colour.BLACK);
            case "n":
                return new Knight(board, Colour.BLACK);
            case "k":
                return new King(board, Colour.BLACK);
            case "q":
                return new Queen(board, Colour.BLACK);
        }

        throw new IllegalArgumentException("Unrecognized piece");
    }

    @Override
    public ChessBoard convertToEntityAttribute(String chessBoardStringRepresentation) {

        String[] allPicesAndPositions = chessBoardStringRepresentation.split(","); //{"P_A5" "p_A6" "B_B9"}
        ChessBoard board = new ChessBoard();
        for (String element: allPicesAndPositions) {
            String[] pieceAndPosition = element.split("_"); // { "P" "A5" }
            String pieceAsString = pieceAndPosition[0]; // {P}
            String positionAsString = pieceAndPosition[1]; // {A5},
            board.setAt(positionAsString,  expand(pieceAsString, board) );

        }

        return board;
    }
}