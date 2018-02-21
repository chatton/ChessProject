package ie.gmit.sw.services;

import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.game.Game;
import ie.gmit.sw.chess.game.Player;
import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.repositories.GameRepository;
import ie.gmit.sw.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessService {

    private final static Logger LOG = LoggerFactory.getLogger(ChessService.class);

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    @Autowired
    public ChessService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public GameState getGameState(int gameId, int playerId) {

        Game game = gameRepository.findOne(gameId);
        // TODO handle game not found
        return game.getGameState(playerId);
    }


    public NewGameResponse newGame(int playerId) { // TODO implement player game list and add this game to it.
        Iterable<Game> allGames = gameRepository.findAll();

        // TODO don't make a new player each time. Save these.
        Player player = new Player();


        for (Game game : allGames) {
            if (game.isFree()) {
                LOG.info("Game was free. Adding player to game: [{}]", game.getId());
                playerRepository.save(player); // updates player id
                player.addGame(game);


                game.addPlayer(player); // put player in that game.
                gameRepository.save(game); // updates game id

                LOG.info("Adding Player - id [{}] to game [{}] as the [{}] player.",
                        player.getId(), game.getId(), game.getColourFor(player.getId()));
                return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
            }
        }

        LOG.info("Found no free games. Making new game.");
        Game game = new Game(ChessFactory.newStandardChessBoard());


        player.addGame(game);
        playerRepository.save(player);
        game.addPlayer(player);
        gameRepository.save(game);

        LOG.info("Generating Player with player id [{}] and adding to game [{}]", player.getId(), game.getId());
        return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
    }

    public void makeMove(Move move, int gameId, int playerId) {
        try {
            Game game = gameRepository.findOne(gameId);
            Player player = playerRepository.findOne(playerId);
            if (player.inGame(game)) {
                game.makeMove(move, playerId);
                gameRepository.save(game); // save the game state to the database so it will persist.
            } else {
                LOG.warn("Player tried to make a move in a game they weren't in!");
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("{} -> {} was an illegal move.", move.from(), move.to());
        }
    }
}
