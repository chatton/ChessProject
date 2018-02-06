package ie.gmit.sw.services;

import com.fasterxml.jackson.annotation.JsonValue;
import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
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

@Service
public class ChessService {

    private Map<Integer, Game> games;

    public ChessService(){
        games = new HashMap<>();
        ChessBoard board = ChessFactory.newStandardChessBoard();
        board.setAt("A8", null);
        board.setAt("D4", new Pawn(board, Colour.WHITE));
        board.setAt("D5", new Knight(board, Colour.BLACK));
        board.setAt("H5", new Rook(board, Colour.BLACK));
        Game game = new Game(new Player(1, Colour.WHITE), new Player(2, Colour.BLACK), board);
        games.put(1, game);
    }

    public GameState getGameState(int gameId){
        // make sure gameId exists
        // TODO handle errors and don't hard code
        return games.get(gameId).getGameState();
    }

    public NewGameResponse newGame() {
        // TODO implement and remove hard coded response.
        // determine if there is a free game to join
        // put player in that game.
        // otherwise, make a new game, put in player in that game.
        return new NewGameResponse(1, 123, Colour.WHITE);
    }
}
