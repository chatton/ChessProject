package ie.gmit.sw.chess.board;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Position)){
            return false;
        }
        Position otherPoint = (Position) obj;
        return otherPoint.x == x && otherPoint.y == y;
    }

    @Override
    public int hashCode() {
        return x | y;
    }
}
