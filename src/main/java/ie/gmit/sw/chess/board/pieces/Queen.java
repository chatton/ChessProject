package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.List;

public class Queen extends Piece {

    private final Rook rook;
    private final Bishop bishop;

    public Queen(ChessBoard board, Colour colour) {
        super(board, colour);
        this.rook = new Rook(board, colour);
        rook.setPosition(position);
        this.bishop = new Bishop(board, colour);
        this.bishop.setPosition(position);
    }

    @Override
    public List<Position> getPossiblePositions() {
        List<Position> possiblePositions = rook.getPossiblePositions();
        possiblePositions.addAll(bishop.getPossiblePositions());
        return possiblePositions;
    }
}
