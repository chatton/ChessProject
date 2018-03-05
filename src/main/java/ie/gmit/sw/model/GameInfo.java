package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.pieces.Colour;

public class GameInfo {

    private String whitePlayerName;
    private String blackPlayerName;
    private int gameId;
    private Colour currentTurn;

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }

    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    public void setBlackPlayerName(String blackPlayerName) {
        this.blackPlayerName = blackPlayerName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Colour getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Colour currentTurn) {
        this.currentTurn = currentTurn;
    }
}
