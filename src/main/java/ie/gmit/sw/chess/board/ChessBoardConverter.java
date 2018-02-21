package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Piece;
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

    @Override
    public ChessBoard convertToEntityAttribute(String chessBoardStringRepresentation) {
        // TODO implement
        return null;
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
}