package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Game class will be used for keeping track of a
 * single game between two players.
 */
public class Game {

    private final static Logger LOG = LoggerFactory.getLogger(Game.class);

    private Colour currentTurnColour;

    private final ChessBoard chessBoard;
    private final int id;

    private final Map<Integer, Player> players;

    public Game(ChessBoard chessBoard, int id) {
        currentTurnColour = Colour.WHITE; // all games start on the white player's turn.
        this.chessBoard = chessBoard;
        this.id = id;
        players = new HashMap<>();
    }

    /**
     * @param playerId
     * @return returns the Colour for the player with the provided ID.
     */
    public Colour getColourFor(int playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player with id: [" + playerId + "] does not exist in this game.");
        }
        return player.getColour();
    }


    /**
     * This methods adds the given player to the game.
     * Throws an exception if there is no room available.
     *
     * @param player the player to add to the game.
     */
    public void addPlayer(Player player) {
        if (!isFree() || players.containsValue(player)) {
            throw new IllegalArgumentException("Game is already full!");
        }

        players.put(player.getId(), player);
        player.setColour(players.size() == 1 ? Colour.WHITE : Colour.BLACK);
        LOG.info("Player [{}] joining game [{}] as the [{}] player.", player.getId(), id, player.getColour());
    }


    /**
     * @return true/false for if there is room in the game for a player to join.
     */
    public boolean isFree() {
        return players.size() < 2;
    }

    public GameState getGameState(int playerId) {
        // TODO determine what the game status is "READY", "ONGOING", "FINISHED"

        Colour playerColour = getColourFor(playerId);
        GameState state = new GameState();

        state.setPositions(chessBoard);
        state.setCurrentTurn(currentTurnColour);
        state.setYourColour(playerColour);

        boolean whiteInCheck = chessBoard.isCheck(Colour.WHITE);
        boolean blackInCheck = chessBoard.isCheck(Colour.BLACK);
        Map<String, Map<String, String>> check = new HashMap<>();
        check.put("BLACK", new HashMap<>());
        check.put("WHITE", new HashMap<>());
        check.get("BLACK").put("inCheck", "" + blackInCheck);
        check.get("BLACK").put("location", chessBoard.getKing(Colour.BLACK).getPosition().toString());
        check.get("WHITE").put("inCheck", "" + whiteInCheck);
        check.get("WHITE").put("location", chessBoard.getKing(Colour.WHITE).getPosition().toString());
        state.setCheck(check);

        return state;
    }

    // true/false for if the given move is for the current player.
    private boolean moveIsForCurrentColour(Move move) {

        if (chessBoard.posIsEmpty(move.from())) { // looking at where the move is starting.
            return false;
        }

        Piece piece = chessBoard.getAt(move.from());
        return piece.getColour() == currentTurnColour;
    }

    // swap the current player's turn.
    private void swapTurn() {
        if (currentTurnColour == Colour.WHITE) {
            currentTurnColour = Colour.BLACK;
        } else {
            currentTurnColour = Colour.WHITE;
        }
    }

    // TODO make sure each player can only move their pieces.
    public void makeMove(Move move, int playerId) {

        if (isFree()) { // nobody should be able to play until 2 players are in the game.
            LOG.warn("Player [{}] the [{}] player attempted to make a move while there wasn't another player yet.",
                    playerId, getColourFor(playerId));
            return;
        }

        // both player are in the game from here on.

        // make sure the player making the move is the player of that colour.
        Colour playerColour = getColourFor(playerId);
        if (playerColour != currentTurnColour) {
            LOG.warn("Player [{}] the [{}] player attempted to make a move when it wasn't their turn.",
                    playerId, getColourFor(playerId));
            return;
        }

        // the the colour of the move (black/white)
        if (!moveIsForCurrentColour(move)) {
            // TODO return error of some kind
            return;
        }
        // make sure it's the current colour.
        // if so, make the move
        // otherwise don't make the move!
        // update current turn if move was made
        LOG.info("[{}] player moved their [{}] from [{}] to [{}].",
                getColourFor(playerId), chessBoard.getAt(move.from()).getName().substring(1), move.from(), move.to());
        chessBoard.makeMove(move);

        // if the move was made successfully, swap turn.
        swapTurn();
    }


    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Game)) {
            return false;
        }
        return id == ((Game) obj).id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
