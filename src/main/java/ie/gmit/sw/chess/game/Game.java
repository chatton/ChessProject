package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessBoardConverter;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Piece;
import ie.gmit.sw.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game class will be used for keeping track of a
 * single game between two players.
 */

@Entity
@Table(name = "GAMES")
public class Game {

    @Id
    @GeneratedValue
    private int id;

    @ElementCollection
    private Map<Integer, Colour> playerColourMap;

    @Column(name = "white_id")
    private Integer whitePlayerId = -1;

    @Column(name = "black_id")
    private Integer blackPlayerId = -1;

    @ManyToMany(mappedBy = "games")
    private List<Player> players;

    @Transient
    private final static Logger LOG = LoggerFactory.getLogger(Game.class);

    @Enumerated(EnumType.STRING)
    private Colour currentTurnColour;

    @Convert(converter = ChessBoardConverter.class)
    private ChessBoard chessBoard;


    public Game() {
        playerColourMap = new HashMap<>();
        players = new ArrayList<>();
        currentTurnColour = Colour.WHITE; // all games start on the white player's turn.
    }

    public Game(ChessBoard chessBoard) {
        this();
        this.chessBoard = chessBoard;
    }


    private Player getById(int id) {
        return players.stream().filter(player -> player.getId() == id).findFirst().orElse(null);
    }

    /**
     * @param playerId
     * @return returns the Colour for the player with the provided ID.
     */
    public Colour getColourFor(int playerId) {
        Player player = getById(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player with id: [" + playerId + "] does not exist in this game.");
        }
        return playerColourMap.get(player.getId());
    }


    /**
     * This methods adds the given player to the game.
     * Throws an exception if there is no room available.
     *
     * @param player the player to add to the game.
     */
    public void addPlayer(Player player) {
        players.add(player);
        playerColourMap.put(player.getId(), players.size() == 1 ? Colour.WHITE : Colour.BLACK);

        if (playerColourMap.get(player.getId()) == Colour.WHITE) {
            whitePlayerId = player.getId();
        } else {
            blackPlayerId = player.getId();
        }

        LOG.info("Player [{}] joining game [{}] as the [{}] player.", player.getId(), id, playerColourMap.get(player.getId()));
    }


    /**
     * @return true/false for if there is room in the game for a player to join.
     */
    public boolean isFree() {
        return players.size() < 2;
    }

    public GameState getGameState(int playerId) {
        Colour playerColour = getColourFor(playerId);
        GameState state = new GameState();

        state.setPositions(chessBoard);
        state.setCurrentTurn(currentTurnColour);
        state.setYourColour(playerColour);
        state.setCheck(buildCheckInfo());
        Colour checkMate = determineCheckMate(); // BLACK / WHITE / null
        state.setWinner(checkMate);
        boolean isCheckMate = checkMate != null;
        state.setGameStatus(determineStatus(isCheckMate));
        return state;
    }

    private GameStatus determineStatus(boolean isCheckMate) {
        if (isFree()) {
            return GameStatus.WAITING;
        }
        if (isCheckMate) {
            return GameStatus.FINISHED;
        }
        return GameStatus.ONGOING;
    }

    private Colour determineCheckMate() {
        boolean blackCheckMate = chessBoard.isCheckMate(Colour.BLACK);
        if (blackCheckMate) {
            return Colour.BLACK;
        }

        boolean whiteCheckMate = chessBoard.isCheckMate(Colour.WHITE);
        if (whiteCheckMate) {
            return Colour.WHITE;
        }
        return null; // means nobody in checkmate.
    }

    private Map<String, Map<String, String>> buildCheckInfo() {
        boolean whiteInCheck = chessBoard.isCheck(Colour.WHITE);
        boolean blackInCheck = chessBoard.isCheck(Colour.BLACK);
        Map<String, Map<String, String>> check = new HashMap<>();
        check.put("BLACK", new HashMap<>());
        check.put("WHITE", new HashMap<>());
        check.get("BLACK").put("inCheck", "" + blackInCheck);
        check.get("BLACK").put("location", chessBoard.getKing(Colour.BLACK).getPosition().toString());
        check.get("WHITE").put("inCheck", "" + whiteInCheck);
        check.get("WHITE").put("location", chessBoard.getKing(Colour.WHITE).getPosition().toString());
        return check;
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
        LOG.info("It is now the [{}] player's turn.", currentTurnColour);
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

    public void setId(int id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
