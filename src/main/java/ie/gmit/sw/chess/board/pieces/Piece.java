package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Piece {

    protected final ChessBoard board;
    protected final Colour colour;
    protected Position position;

    public Piece(ChessBoard board, Colour colour) {
        this.board = board;
        this.colour = colour;
    }

    public Collection<Move> getPossibleMoves() {
        Collection<Position> positions = getPossiblePositions();
        List<Move> moves = new ArrayList<>();
        for (Position pos : positions) {
            moves.add(new Move(this.position, pos));
        }
        return moves;

    }

    public String getName() {

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

    public abstract Collection<Position> getPossiblePositions();

}
