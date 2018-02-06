package ie.gmit.sw.chess.board;

import ie.gmit.sw.utilities.Util;

/**
 * A Move object represents a single player movement
 * on the chess board from one position to another.
 */
public class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Move(String from, String to) {
        this(Util.stringToPosition(from, 8), Util.stringToPosition(to, 8));
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }
}
