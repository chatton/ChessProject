package ie.gmit.sw.services;

import ie.gmit.sw.ChessProjectApplication;
import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.game.Game;
import ie.gmit.sw.chess.game.Player;
import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.repositories.ChessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ChessService {

    private final static Logger LOG = LoggerFactory.getLogger(ChessService.class);

    private Map<Integer, Game> games;

    private ChessRepository repository;

    @Autowired
    public ChessService(ChessRepository repository) {
        games = new HashMap<>();
        // TODO populate from DB
    }

    public GameState getGameState(int gameId) {
        // make sure gameId exists
        // TODO handle errors and don't hard code
        return games.get(gameId).getGameState();
    }

    // TODO handle duplicate ids generated.
    private Player generatePlayer() {
        Random rnd = new Random();
        int playerId = rnd.nextInt();
        LOG.info("Generating Player with player id [{}]", playerId);
        return new Player(playerId);
    }

    private Game generateGame() {
        Random rnd = new Random();
        ChessBoard board = ChessFactory.newStandardChessBoard();
        int gameId = rnd.nextInt();
        LOG.info("Generating Game with game id [{}]", gameId);
        return new Game(board, gameId);
    }

    public NewGameResponse newGame() {
        // there are existing games to join.
        for (Game game : games.values()) { // look at all existing games
            // determine if there is a free game to join
            if (game.isFree()) {
                LOG.info("Game was free. Adding player to game: [{}]", game.getId());
                Player player = generatePlayer();
                game.addPlayer(player); // put player in that game.
                return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
            }
        }

        LOG.info("Found no free games. Making new game.");
        Game game = generateGame();
        Player player = generatePlayer();
        game.addPlayer(player);
        games.put(game.getId(), game);
        LOG.info("Saving game: [{}]", game.getId());
        return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
    }

    public void makeMove(Move move, int gameId, int playerId) {
        try {
            games.get(gameId).makeMove(move, playerId);
        } catch (IllegalArgumentException e) {
            LOG.warn("{} -> {} was an illegal move.", move.from(), move.to());
        }
    }
}
