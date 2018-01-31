package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ChessBoard {

    private final Piece[][] board;
    private final int rows;
    private final int cols;

    private int turnNo;

    public ChessBoard(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.board = new Piece[rows][cols];
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
    public Piece getAt(Position pos){
        // TODO handle index out of bounds errors here. IndexOutOfBoundsException possible
        return board[pos.x()][pos.y()];
    }

    /**
     * @param pos the position the new piece will be inserted into
     * @param piece the piece that is to be inserted.
     */
    public void setAt(Position pos, Piece piece){
        board[pos.x()][pos.y()] = piece;
    }

    /**
     * @param pos the position in question.
     * @return true or false for if the position is unoccupied.
     */
    public boolean posIsEmpty(Position pos){
        return getAt(pos) == null;
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
    public boolean moveIsValid(Move move){
        throw new NotImplementedException(); // TODO implement
    }

    /**
     * Making a move will update the piece at the from position and move it to the
     * to position. You should check if the move is valid before calling this method.
     * use "moveIsValid"
     *
     * @param move the move that will update the board state.
     */
    public void makeMove(Move move) {
        throw new NotImplementedException(); // TODO implement
    }

    /**
     * @return a list of all the non-null pieces in the game.
     */
    public List<Piece> getPieces() {
        throw new NotImplementedException(); // TODO implement
    }


    /**
     * @param colour either BLACK or WHITE
     * @return all the non-null pieces of that colour.
     */
    public List<Piece> getPieces(Colour colour){
        throw new NotImplementedException(); // TODO implement
    }
}
