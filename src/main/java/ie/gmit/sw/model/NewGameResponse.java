package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.pieces.Colour;

/**
 * Represents the response that is sent to the client
 * when they request a new game
 */
public class NewGameResponse {
    private int gameId;
    private int payerId;
    private Colour colour;

    public NewGameResponse(int gameId, int payerId, Colour colour) {
        this.gameId = gameId;
        this.payerId = payerId;
        this.colour = colour;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPayerId() {
        return payerId;
    }

    public Colour getColour() {
        return colour;
    }
}
