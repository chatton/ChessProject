package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {
    public Knight(ChessBoard board, Colour colour) {

        super(board, colour);
    }
    @Override
    public String getName(){
        return super.getName() + "Knight";
    }

    @Override
    public List<Position> getPossiblePositions() {

        List<Position> validPositions = new ArrayList<>();
        List<Position> positions = Arrays.asList(
                new Position(position.x() + 1, position.y() - 2),
                new Position(position.x() -1, position.y() - 2),
                new Position(position.x() + 1, position.y() + 2),
                new Position(position.x() - 1, position.y() +2),
                new Position(position.x() + 2, position.y() - 1),
                new Position(position.x() + 2, position.y() + 1),
                new Position(position.x() - 2, position.y() - 1),
                new Position(position.x() - 2, position.y() + 1)
        );

        // TODO Handle index out of bounds

        // TODO handle check/checkmate validation!

        for (Position pos : positions) {
            addIfValid(validPositions, pos);
        }

        return validPositions;
    }

    private void addIfValid(List<Position> positions, Position pos) {
        if(!board.isOnBoard(pos)){
            return;
        }

        if (board.posIsEmpty(pos)) {
            positions.add(pos);
        } else {
            Piece piece = board.getAt(pos);
            if (piece.colour != colour) {
                positions.add(pos);
            }
        }
    }
}
