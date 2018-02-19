package ie.gmit.sw.chess.board;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ChessBoardConverter implements AttributeConverter<ChessBoard, String> {

    @Override
    public String convertToDatabaseColumn(ChessBoard board) {
        // TODO implement
        return null;
    }

    @Override
    public ChessBoard convertToEntityAttribute(String chessBoardStringRepresentation) {
        // TODO implement
        return null;
    }
}
