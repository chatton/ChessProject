package ie.gmit.sw.services;

import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.game.Game;
import ie.gmit.sw.chess.game.Player;
import ie.gmit.sw.model.GameInfo;
import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.LoginRequest;
import ie.gmit.sw.model.LoginResponse;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.model.RegisterRequest;
import ie.gmit.sw.model.RegistrationResponse;
import ie.gmit.sw.repositories.GameRepository;
import ie.gmit.sw.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class ChessService {

    private final static Logger LOG = LoggerFactory.getLogger(ChessService.class);

    private Random rnd;
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    @Autowired
    public ChessService(PlayerRepository playerRepository, GameRepository gameRepository) {
        rnd = new Random();
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public GameState getGameState(int gameId, int playerId) {

        Game game = gameRepository.findOne(gameId);
        // TODO handle game not found / doesn't exist

        GameState gameState = game.getGameState(playerId);
        return gameState; // this will contain info that the game is finished for the clients.
    }

    private Game generateGame(boolean isPrivate){
        Game game = new Game(ChessFactory.newStandardChessBoard(), Math.abs(rnd.nextInt()));
        game.setIsPrivate(isPrivate);
        return game;
    }

    public NewGameResponse joinPrivateGame(int gameId, int playerId) {
        Player player = playerRepository.findOne(playerId);
        List<Game> privateGames = gameRepository.findPrivateGames();

        for(Game game : privateGames){
            if(game.getId() == gameId && game.isFree()){
                player.addGame(game);
                game.addPlayer(player);
                playerRepository.save(player);
                return new NewGameResponse(gameId, playerId, game.getColourFor(playerId));
            }
        }
        return null; // no game by that id!
    }

    public NewGameResponse newGame(int playerId, boolean isPrivate) {


        // Find the player with the given id.
        Player player = playerRepository.findOne(playerId);

        // TODO handle invalid player

        if(isPrivate){ // the user wants to start a private game.
            Game game = generateGame(true);
            player.addGame(game);
            game.addPlayer(player);
            playerRepository.save(player);
            return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
        }

        Iterable<Game> allGames = gameRepository.findAll();

        for (Game game : allGames) { // only look at public games that have a free space
            if (game.isFree() && !game.isPrivate() && !game.contains(player)) {
                LOG.info("Game was free. Adding player to game: [{}]", game.getId());
                player.addGame(game);
                game.addPlayer(player); // put player in that game.
                playerRepository.save(player); // updates player id

                LOG.info("Adding Player - id [{}] to game [{}] as the [{}] player.",
                        player.getId(), game.getId(), game.getColourFor(player.getId()));
                return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
            }
        }

        LOG.info("Found no free games. Making new public game.");
        Game game = generateGame(false);
        player.addGame(game);
        game.addPlayer(player);
        playerRepository.save(player);

        LOG.info("adding player with id [{}] and adding to game [{}]", player.getId(), game.getId());
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

    public RegistrationResponse register(RegisterRequest request) {
        // validate the entered values.
        boolean badUserName = request.getUserName().trim().isEmpty();
        boolean badPassword = request.getPassword().trim().isEmpty();
        if(badPassword || badUserName){
            return new RegistrationResponse(null, "BAD");
        }

        // create the player to add.
        Player playerToRegister = new Player(request.getUserName(), request.getPassword());
        Player playerWithName = playerRepository.findByName(playerToRegister.getName());
        boolean nameInUse = playerWithName != null;

        if(nameInUse){
            return new RegistrationResponse(null, "BAD");
        }

        playerRepository.save(playerToRegister); // register the player in the database.
        return new RegistrationResponse(playerToRegister.getId(), "OK");
    }

    public LoginResponse login(LoginRequest request) {
        // request has user name and password

        // check to see if a user exists with the user name
        Player player = playerRepository.findByName(request.getUserName());
        if(player == null){ // player doesn't exist!
            // return error
            return new LoginResponse(null, "BAD");
        }

        // check if they have the right password
        if(request.getPassword().hashCode() == player.getPasswordHash()){
            // the user exists, and they have the right password
            // return the info for that user.
            return new LoginResponse(player.getId(), "OK");
        } else {
            // user exists, they have the wrong password
            return new LoginResponse(null, "BAD");
        }
    }

    public List<GameInfo> getAllGames(int playerId) {
        Player player = playerRepository.findOne(playerId);

        if(player == null){
            // TODO handle player not in db
        }

        Collection<Game> games = player.getGames();

        List<GameInfo> allGameInfo = new ArrayList<>();

        for(Game game : games){
            GameInfo info = new GameInfo();
            info.setGameId(game.getId());

            Player whitePlayer = game.getPlayerByColour(Colour.WHITE);
            info.setWhitePlayerName(whitePlayer == null ? null : whitePlayer.getName());

            Player blackPlayer = game.getPlayerByColour(Colour.BLACK);
            info.setBlackPlayerName(blackPlayer == null ? null : blackPlayer.getName());

            info.setCurrentTurn(game.getCurrentTurnColour());

            info.setGameStatus(game.getGameState(playerId).getGameStatus());
            allGameInfo.add(info);
        }

        return allGameInfo;
    }
}