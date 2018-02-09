package ie.gmit.sw.services;

import com.fasterxml.jackson.annotation.JsonValue;
import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.Knight;
import ie.gmit.sw.chess.board.pieces.Pawn;
import ie.gmit.sw.chess.board.pieces.Rook;
import ie.gmit.sw.chess.game.Game;
import ie.gmit.sw.chess.game.Player;
import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.NewGameResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ChessService {

    private Map<Integer, Game> games;

    public ChessService() {
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
        System.out.println("Generating Player...");
        Random rnd = new Random();
        int playerId = rnd.nextInt();
        return new Player(playerId);
    }

    private Game generateGame() {
        System.out.println("Generating Game...");
        ChessBoard board = ChessFactory.newStandardChessBoard();
        Random rnd = new Random();
        int gameId = rnd.nextInt();
        return new Game(board, gameId);
    }

    public NewGameResponse newGame() {
        // there are existing games to join.
        for (Game game : games.values()) { // look at all existing games
            // determine if there is a free game to join
            if (game.isFree()) {
                System.out.println("Game was free. Adding player to game: " + game.getId());
                Player player = generatePlayer();
                game.addPlayer(player); // put player in that game.
                return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
            }
        }

        System.out.println("Found no free games. Making new game.");
        Game game = generateGame();
        Player player = generatePlayer();
        game.addPlayer(player);
        games.put(game.getId(), game);
        System.out.println("Saving game: " + game.getId());
        return new NewGameResponse(game.getId(), player.getId(), game.getColourFor(player.getId()));
    }

    public void makeMove(Move move, int gameId) {
        games.get(gameId).makeMove(move);
    }
}
