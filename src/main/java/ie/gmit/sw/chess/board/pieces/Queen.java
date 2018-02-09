package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    private final Rook rook;
    private final Bishop bishop;

    public Queen(ChessBoard board, Colour colour) {
        super(board, colour);
        this.rook = new Rook(board, colour);
        this.bishop = new Bishop(board, colour);
    }

    @Override
    public String getName() {
        return super.getName() + "Queen";
    }

    @Override
    public Collection<Position> getPossiblePositions() {
        // reset the position each time so the moves generated are correct.
        rook.setPosition(position);
        bishop.setPosition(position);
        Collection<Position> possiblePositions = rook.getPossiblePositions();
        possiblePositions.addAll(bishop.getPossiblePositions());
        return possiblePositions;
    }
}
