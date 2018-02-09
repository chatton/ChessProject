package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.utilities.Util;

/**
 * A Move object represents a single player movement
 * on the chess board from one position to another.
 */
public class Move {
    private final Position from;
    private final Position to;
    private Piece fromPiece;
    private Piece toPiece;

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

    public Move reverse(){
        Move reversed = new Move(to, from);
        reversed.setToPiece(fromPiece);
        reversed.setFromPiece(toPiece);
        return reversed;
    }

    public void setFromPiece(Piece fromPiece) {
        this.fromPiece = fromPiece;
    }

    public Piece getToPiece() {
        return toPiece;
    }

    public void setToPiece(Piece toPiece) {
        this.toPiece = toPiece;
    }

    public Piece getFromPiece() {
        return fromPiece;
    }
}
