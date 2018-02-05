package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.utilities.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    private Map<String, String> positions;

    public GameState(ChessBoard board) {
        positions = new HashMap<>();

        // all the pieces on the board (32 pieces)
        List<Piece> allPieces = board.getPieces();
        for (Piece piece : allPieces) {
            // get the chess notation (A1, A2 etc) from the (7,7) (7,6) co-ordinates
            String chessNotation = Util.positionToString(piece.getPosition(), board.size());
            positions.put(chessNotation, piece.getName());
        }
    }

    public Map<String, String> getPositions() {
        return positions;
    }
}
