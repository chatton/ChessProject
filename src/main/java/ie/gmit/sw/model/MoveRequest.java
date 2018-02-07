package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.Move;

/**
 * This class represent a request to perform
 * an action on a particular game. Sent in by
 * clients of the API.
 */
public class MoveRequest {

    private String from;
    private String to;
    private int gameId;

    public Move getMove() {
        return new Move(from, to);
    }

    public int getGameId() {
        return gameId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
