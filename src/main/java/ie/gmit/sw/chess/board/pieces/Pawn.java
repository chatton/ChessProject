package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.Collection;
import java.util.HashSet;

public class Pawn extends Piece {
    public Pawn(ChessBoard board, Colour colour) {
        super(board, colour);
    }

    @Override
    public String getName() {
        return super.getName() + "Pawn";
    }

    @Override
    public Collection<Position> getPossiblePositions() {
        Collection<Position> validPositions = new HashSet<>();
        if (colour == Colour.WHITE) {
            // get the position directly in front
            Position inFront = new Position(position.x(), position.y() - 1);
            addIfFrontPositionValid(validPositions, inFront);

            Position[] diagonals = {
                    new Position(position.x() + 1, position.y() - 1),
                    new Position(position.x() - 1, position.y() - 1)
            };
            // add these to validPositions
            addIfDiagonalsValid(validPositions, diagonals);
            addIfSecondSpotIsValid(validPositions);


        } // else for BLACK pieces
        else {
            // the piece is black
            Position inFront = new Position(position.x(), position.y() + 1);
            addIfFrontPositionValid(validPositions, inFront);

            Position[] diagonals = {
                    new Position(position.x() + 1, position.y() + 1),
                    new Position(position.x() - 1, position.y() + 1)
            };
            // add these to validPositions
            addIfDiagonalsValid(validPositions, diagonals);
            addIfSecondSpotIsValid(validPositions);
        }//else

        return validPositions;

    }

    private void addIfSecondSpotIsValid(Collection<Position> validPositions) {
        if (colour.equals(Colour.WHITE)) {
            if (position.y() != 6) { // 6 is the starting position of white pawns.
                return;
            }


            boolean positionInFrontIsEmpty = board.posIsEmpty(new Position(position.x(), position.y() - 1));
            boolean positionsTwoSpacesInFrontIsEmpty = board.posIsEmpty(new Position(position.x(), position.y() - 2));

            if (positionInFrontIsEmpty && positionsTwoSpacesInFrontIsEmpty) {
                validPositions.add(new Position(position.x(), position.y() - 2));
            }

        } else { // BLACK
            if (this.position.y() != 1) { // 1 is the starting position of black pawns.
                return;
            }
            boolean positionInFrontIsEmpty = board.posIsEmpty(new Position(position.x(), position.y() + 1));
            boolean positionsTwoSpacesInFrontIsEmpty = board.posIsEmpty(new Position(position.x(), position.y() + 2));

            if (positionInFrontIsEmpty && positionsTwoSpacesInFrontIsEmpty) {
                validPositions.add(new Position(position.x(), position.y() + 2));
            }
        }
    }

    private void addIfFrontPositionValid(Collection<Position> positions, Position pos) {
        // white row
        if (position.y() == board.size() - 1) { // white pawns are in the second last row

        } else if (position.y() == 1) { // back row

        }
        if (!board.isOnBoard(pos)) {
            return;
        }
        if (!board.isOnBoard(pos)) {
            return;
        }
        // check if it's empty
        if (board.posIsEmpty(pos)) {
            positions.add(pos);
        }
    }

    private void addIfDiagonalsValid(Collection<Position> validPositions, Position[] positions) {
        for (Position pos : positions) {
            if (!board.isOnBoard(pos)) {
                continue;//look at every position in the array
            }
            // check if it's empty
            if (board.posIsEmpty(pos)) {
                continue;
            }
            //check if there is an enemy piece
            Piece piece = board.getAt(pos);
            if (piece.colour != colour) {
                validPositions.add(pos);
            }

        }
    }
}