package ie.gmit.sw.chess.board;

import ie.gmit.sw.chess.board.pieces.Piece;

public class ChessBoard {
    private Piece[][] board;

    public Piece getAt(Position pos){
        // TODO handle index out of bounds errors here. IndexOutOfBoundsException possible
        return board[pos.x()][pos.y()];
    }

    public void setAt(Position pos, Piece piece){
        board[pos.x()][pos.y()] = piece;
    }

    public boolean posIsEmpty(Position pos){
        return getAt(pos) == null;
    }



}
