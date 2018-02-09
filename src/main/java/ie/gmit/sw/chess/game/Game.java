package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.model.GameState;

/**
 * Game class will be used for keeping track of a
 * single game between two players.
 */
public class Game {

    private Player player1;
    private Player player2;
    private Colour currentTurn;

    private final ChessBoard chessBoard;
    private final int id;

    public Game(ChessBoard chessBoard, int id) {
        currentTurn = Colour.WHITE; // all games start on the white player's turn.
        this.chessBoard = chessBoard;
        this.id = id;
    }

    /**
     * @param playerId
     * @return returns the Colour for the player with the provided ID.
     */
    public Colour getColourFor(int playerId) {
        if (player1.getId() == playerId) {
            return player1.getColour();
        } else if (player2.getId() == playerId) {
            return player2.getColour();
        }
        throw new IllegalArgumentException("Player with id: [" + playerId + "] does not exist in this game.");
    }


    /**
     * This methods adds the given player to the game.
     * Throws an exception if there is no room available.
     *
     * @param player the player to add to the game.
     */
    public void addPlayer(Player player) {
        System.out.println("Player: " + player.getId() + " joining game: " + id);

        if (player1 == null) {
            player1 = player;
            player.setColour(Colour.WHITE);
            return;
        }

        if (player2 == null) {
            player2 = player;
            player.setColour(Colour.BLACK);
            return;
        }

        throw new IllegalArgumentException("Game is already full!");
    }


    /**
     * @return true/false for if there is room in the game for a player to join.
     */
    public boolean isFree() {
        return player1 == null || player2 == null;
    }

    public GameState getGameState() {
        return new GameState(chessBoard, currentTurn);
    }

    // true/false for if the given move is for the current player.
    private boolean moveIsForCurrentColour(Move move) {
        if (chessBoard.posIsEmpty(move.from())) { // looking at where the move is starting.
            return false;
        }

        Piece piece = chessBoard.getAt(move.from());
        return piece.getColour() == currentTurn;
    }

    // swap the current player's turn.
    private void swapTurn() {
        if (currentTurn == Colour.WHITE) {
            currentTurn = Colour.BLACK;
        } else {
            currentTurn = Colour.WHITE;
        }
    }

    // TODO make sure each player can only move their pieces.
    public void makeMove(Move move) {
        // the the colour of the move (black/white)
        if (!moveIsForCurrentColour(move)) {
            // TODO return error of some kind
            return;
        }
        // make sure it's the current colour.
        // if so, make the move
        // otherwise don't make the move!
        // update current turn if move was made
        chessBoard.makeMove(move);

        // if the move was made successully, swap turn.
        swapTurn();
    }


    public int getId() {
        return id;
    }
}
