package ie.gmit.sw.chess.board;

/**
 * A Move object represents a single player movement
 * on the chess board from one position to another.
 */
public class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to){
        this.from = from;
        this.to = to;
    }

    public Position from(){
        return from;
    }

    public Position to(){
        return to;
    }
}
