package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.pieces.Colour;

/**
 * Represents the response that is sent to the client
 * when they request a new game
 */
public class NewGameResponse {
    private int gameId;
    private int playerId;
    private Colour colour;

    public NewGameResponse(int gameId, int playerId, Colour colour) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.colour = colour;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Colour getColour() {
        return colour;
    }
}
