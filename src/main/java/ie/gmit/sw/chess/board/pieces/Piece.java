package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.List;

public abstract class Piece {

    protected final ChessBoard board;
    protected final Colour colour;
    protected Position position;

    public Piece(ChessBoard board, Colour colour) {
        this.board = board;
        this.colour = colour;
    }


    public String getName(){

        return colour.name().toLowerCase().charAt(0) + "";
    }

    public void setPosition(Position newPos) {

        this.position = newPos;
    }

    public Colour getColour() {
        return colour;
    }

    public Position getPosition() {
        return position;
    }

    public abstract List<Position> getPossiblePositions();

}
