package ie.gmit.sw.utilities;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import static ie.gmit.sw.chess.board.ChessBoard.BOARD_SIZE;

public class Util {

    // prevent instantiation
    private Util() {
    }

    public static Position stringToPosition(String chessNotation) {
        char letter = chessNotation.charAt(0);
        int number = Integer.parseInt(chessNotation.substring(1, chessNotation.length()));
        int x = letter - 'A';
        int y = BOARD_SIZE - number;
        return new Position(x, y);
    }

    public static String positionToString(Position position) {
        int x = position.x();
        int y = position.y();
        char letter = (char) ('A' + x);
        int newY = BOARD_SIZE - y;
        return letter + "" + newY;
    }


}
