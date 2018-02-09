package ie.gmit.sw.chess.board;

import ie.gmit.sw.utilities.Util;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String chessNotation) {
        Position converted = Util.stringToPosition(chessNotation, 8);
        this.x = converted.x();
        this.y = converted.y();

    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Position)) {
            return false;
        }
        Position otherPoint = (Position) obj;
        return otherPoint.x == x && otherPoint.y == y;
    }

    @Override
    public int hashCode() {
        return x | y;
    }

    @Override
    public String toString(){
        return Util.positionToString(this, 8);
    }
}
