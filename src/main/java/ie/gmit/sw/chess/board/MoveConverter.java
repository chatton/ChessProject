package ie.gmit.sw.chess.board;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class MoveConverter implements AttributeConverter<Move, String> {


    @Override
    public String convertToDatabaseColumn(Move move) {
        if (move == null) {
            return null;
        }
        return move.to().toChess() + "_" + move.from().toChess();
    }

    @Override
    public Move convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        String[] fromTo = s.split("_");
        return new Move(fromTo[0], fromTo[1]);
    }
}