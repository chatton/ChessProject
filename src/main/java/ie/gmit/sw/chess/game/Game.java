package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.model.GameState;

/**
 * Game class will be useed for keeping track of a
 * single game between two players.
 */
public class Game {
    private Player player1;
    private Player player2;
    private ChessBoard chessBoard;

    public Game(Player player1, Player player2, ChessBoard chessBoard) {
        this.player1 = player1;
        this.player2 = player2;
        this.chessBoard = chessBoard;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }


    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameState getGameState() {
        return new GameState(chessBoard);
    }

    public void makeMove() {

    }

}
