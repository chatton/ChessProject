package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Knight extends Piece {

    public Knight(ChessBoard board, Colour colour) {
        super(board, colour);
    }

    @Override
    public String getName() {
        return super.getName() + "Knight";
    }

    @Override
    public Collection<Position> getPossiblePositions() {

        Collection<Position> validPositions = new HashSet<>();
        List<Position> positions = Arrays.asList(
                new Position(position.x() + 1, position.y() - 2),
                new Position(position.x() - 1, position.y() - 2),
                new Position(position.x() + 1, position.y() + 2),
                new Position(position.x() - 1, position.y() + 2),
                new Position(position.x() + 2, position.y() - 1),
                new Position(position.x() + 2, position.y() + 1),
                new Position(position.x() - 2, position.y() - 1),
                new Position(position.x() - 2, position.y() + 1)
        );

        for (Position pos : positions) {
            addIfValid(validPositions, pos);
        }

        return validPositions;
    }

    private void addIfValid(Collection<Position> positions, Position pos) {
        if (!board.isOnBoard(pos)) {
            return;
        }

        // it's a valid move if the position is empty or the piece is the opposite colour.
        boolean valid = board.posIsEmpty(pos) || board.getAt(pos).colour != colour;
        if (valid) {
            positions.add(pos);
        }
    }
}
