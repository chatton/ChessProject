package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.chess.game.GameStatus;
import ie.gmit.sw.utilities.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameState class holds all the information
 * that clients need to keep track of an individual
 * game.
 */
public class GameState {

    private Map<String, String> positions;
    private GameStatus gameStatus; // "FINISHED", "ONGOING", "STARTING"
    private String message;
    private Colour winner;
    private Colour currentTurn; // "WHITE" / "BLACK"
    private Colour yourColour;

    private Map<String, Map<String, String>> check;

    public void setPositions(ChessBoard board) {
        positions = new HashMap<>();
        List<Piece> allPieces = board.getPieces();
        for (Piece piece : allPieces) {
            // get the chess notation (A1, A2 etc) from the (7,7) (7,6) co-ordinates
            String chessNotation = Util.positionToString(piece.getPosition());
            positions.put(chessNotation, piece.getName());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setPositions(Map<String, String> positions) {
        this.positions = positions;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWinner(Colour winner) {
        this.winner = winner;
    }

    public void setCurrentTurn(Colour currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setYourColour(Colour yourColour) {
        this.yourColour = yourColour;
    }

    public void setCheck(Map<String, Map<String, String>> check) {
        this.check = check;
    }

    public Colour getWinner() {
        return winner;
    }

    public Map<String, Map<String, String>> getCheck() {
        return check;
    }


    public Map<String, String> getPositions() {
        return positions;
    }

    public Colour getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Colour getYourColour() {
        return yourColour;
    }
}
