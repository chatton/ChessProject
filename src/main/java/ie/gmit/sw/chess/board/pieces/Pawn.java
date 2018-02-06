package ie.gmit.sw.chess.board.pieces;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(ChessBoard board,  Colour colour) {
        super(board, colour);
    }

    @Override
    public String getName(){
        return super.getName() + "Pawn";
    }

    @Override
    public List<Position> getPossiblePositions() {
        List<Position> validPositions = new ArrayList<>();
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
        }//else

        return validPositions;

    }

    private void addIfFrontPositionValid(List<Position> positions, Position pos) {
        // white row
        if(position.y() == board.size() - 1){ // white pawns are in the second last row

        } else if (position.y() == 1){ // back row

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

    private void addIfDiagonalsValid(List<Position> validPositions, Position[] positions) {
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
