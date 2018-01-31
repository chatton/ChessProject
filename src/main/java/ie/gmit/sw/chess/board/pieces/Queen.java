package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.List;

public class Queen extends Piece {

    private final Rook rook;
    private final Bishop bishop;

    public Queen(ChessBoard board, Position pos, Colour colour) {
        super(board, pos, colour);
        this.rook = new Rook(board, pos, colour);
        this.bishop = new Bishop(board, pos, colour);
    }

    @Override
    public List<Position> getPossiblePositions() {
        List<Position> possiblePositions = rook.getPossiblePositions();
        possiblePositions.addAll(bishop.getPossiblePositions());
        return possiblePositions;
    }
}
