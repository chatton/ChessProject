package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.utilities.Util;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    private final Piece[][] board;
    private final int size;

    private int turnNo;

    public ChessBoard(int size) {
        this.size = size;
        this.board = new Piece[size][size];
        this.turnNo = 1;
    }

    /**
     * Returns the Piece at a given location, null if empty.
     * It's best to check whether or not the position is occupied
     * or not via "posIsEmpty" to avoid NullPointerExceptions.
     *
     * @param pos the position in question
     * @return the Piece that occupies the position or null if empty.
     */
    public Piece getAt(Position pos) {
        // TODO handle index out of bounds errors here. IndexOutOfBoundsException possible
        return board[pos.x()][pos.y()];
    }

    public Piece getAt(String chessNotation) {
        return getAt(Util.stringToPosition(chessNotation, size));
    }

    /**
     * @param pos   the position the new piece will be inserted into
     * @param piece the piece that is to be inserted.
     */
    public void setAt(Position pos, Piece piece) {
        board[pos.x()][pos.y()] = piece;
        piece.setPosition(pos);
    }

    public void setAt(String chessNotation, Piece piece) {
        setAt(Util.stringToPosition(chessNotation, size), piece);
    }

    /**
     * @param pos the position in question.
     * @return true or false for if the position is unoccupied.
     */
    public boolean posIsEmpty(Position pos) {
        return getAt(pos) == null;
    }

    public boolean posIsEmpty(String chessNotation) {
        return posIsEmpty(Util.stringToPosition(chessNotation, size));
    }

    /**
     * @return the current turn number.
     */
    public int getTurnNo() {
        return turnNo;
    }


    /**
     * @param move
     * @return true or false for if the provided move is valid with the current board state.
     */
    public boolean moveIsValid(Move move) {

        /*
        The move object represents 2 positions on the board. The piece at move.from()
        should be repositioned to move.to(), we need to confirm the move is valid before
        we mess with the board.
         */

        // get the piece that we are going to move.
        Piece targetPiece = getAt(move.from());
        if (targetPiece == null) { // empty position
            // invalid because the user tried to move from an empty piece to another piece. (not a legal move)
            return false; // it's not a valid move because it's not actually moving a piece.
        }
        // it is a piece we want to move.
        Position destination = move.to(); // the place we will move the piece to.
        List<Position> possibleMoves = targetPiece.getPossiblePositions(); // depends on the piece type what these are.
        // the move is valid if and only if the piece can actually move there. Otherwise it's invalid.
        boolean positionIsValid = possibleMoves.contains(destination); // the piece can go to the destination. i.e. it is a legal move.
        return positionIsValid; // true/false

    }


    /**
     * Making a move will update the piece at the from position and move it to the
     * to position. You should check if the move is valid before calling this method.
     * use "moveIsValid"
     *
     * @param move the move that will update the board state.
     */
    public void makeMove(Move move) {
        // ttry this one. Check if the move is valid using that method,
        // then update the board - so put the piece at move.from() into move.to()
        // then delete this comment!!!!!!!
        throw new NotImplementedException(); // TODO implement
    }

    /**
     * @return a list of all the non-null pieces in the game. Includes black and white pieces.
     */
    public List<Piece> getPieces() {
        List<Piece> allPieces = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    allPieces.add(piece);
                }
            }
        }
        return allPieces;
    }


    /**
     * @param colour either BLACK or WHITE
     * @return all the non-null pieces of that colour.
     */
    public List<Piece> getPieces(Colour colour) {
        // same but only of the colour provided! if piece.getColour() == colour
        throw new NotImplementedException(); // TODO implement
    }

    public boolean isOnBoard(Position pos) {
        return !(pos.x() < 0
                || pos.y() < 0
                || pos.y() >= size()
                || pos.x() >= size());

    }

    public int size() {
        return size;
    }
}
