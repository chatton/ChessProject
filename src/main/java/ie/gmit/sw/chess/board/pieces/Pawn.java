package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Pawn extends Piece {
    public Pawn(ChessBoard board, Position pos, Colour colour) {
        super(board, pos, colour);
    }

    @Override
    public List<Position> getPossiblePositions() {
        throw new NotImplementedException();
    }
}
