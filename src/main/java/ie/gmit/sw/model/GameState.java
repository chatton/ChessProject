package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.utilities.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 The GameState class holds all the information
 that clients need to keep track of an individual
 game.
 */
public class GameState {

    private Map<String, String> positions;
    private String gameStatus; // "FINISHED", "ONGOING", "STARTING"
    private Colour currentTurn; // "WHITE" / "BLACK"

    public GameState(ChessBoard board, Colour colour) {
        positions = new HashMap<>();
        currentTurn = colour;
        // all the pieces on the board (32 pieces)
        List<Piece> allPieces = board.getPieces();
        for (Piece piece : allPieces) {
            // get the chess notation (A1, A2 etc) from the (7,7) (7,6) co-ordinates
            String chessNotation = Util.positionToString(piece.getPosition());
            positions.put(chessNotation, piece.getName());
        }
    }

    public Map<String, String> getPositions() {
        return positions;
    }

    public Colour getCurrentTurn() {
        return currentTurn;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
