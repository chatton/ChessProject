package ie.gmit.sw.utilities;

import ie.gmit.sw.chess.board.Position;

public class Util {

    // prevent instantiation
    private Util() {
    }

    public static Position stringToPosition(String chessNotation, int boardSize) {
        char letter = chessNotation.charAt(0);
        int number = Integer.parseInt(chessNotation.substring(1, chessNotation.length()));
        int x = letter - 'A';
        int y = boardSize - number;
        return new Position(x, y);
    }

    public static String positionToString(Position position, int boardSize) {
        int x = position.x();
        int y = position.y();
        char letter = (char) ('A' + x);
        int newY = boardSize - y;
        return letter + "" + newY;
    }


}
