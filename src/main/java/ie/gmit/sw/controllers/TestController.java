package ie.gmit.sw.controllers;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.Move;
import ie.gmit.sw.chess.game.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
this controller exists just to practice the different
annotations and functionality available.
 */
@RestController
public class TestController {

//    @RequestMapping("/test")
//    public ResponseEntity<Message> test() {
//        return new ResponseEntity<>(new Message("Hello World", 123), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/games")
//    public ResponseEntity<Message> addOne(@RequestBody Message message) {
//        message.inc();
//        return ResponseEntity.ok(message);
//    }


//    public String makeMove(RequestParam("from") String from , @RequestParam("to") String to) {
//return "";
//    }

//    @RequestMapping("/chess/v1/gamestate/")
//    public ie.gmit.sw.model.GameState getGameState(@RequestParam("gameId") int id) {
//        ChessBoard board = ChessFactory.newStandardChessBoard();
//        System.out.println("GAME ID WAS :" + id);
//        return new ie.gmit.sw.model.GameState(board);
//    }

//    @PostMapping("makemove")
//    public void makeMove(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("gameId") int gameId) {
//        Move move = new Move(from, to);
//        System.out.println(from + " -> " + to);
//        chessService.makeMove(move, gameId);
//    }
//
//PostMapping("makemove")
//    public void makeMove(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("gameId") int gameId) {
//        Move move = new Move(from, to);
//        System.out.println(from + " -> " + to);
//        chessService.makeMove(move, gameId);
//    }
//

    // /chess/v1/newgame
    // give back the new game's id and if it's ready or not
//    @GetMapping("/chess/v1/newgame")
//    public InitialResponse getInitialResponse() {
//        return new InitialResponse(456, 2, Colour.BLACK);
//    }


//    public void makeMove(Move move, int gameId) {
//        Game game = games.get(gameId);
//        game.makeMove(move);
//    }

}


/*
package ie.gmit.sw.model;

import ie.gmit.sw.chess.board.pieces.Colour;

public class InitialResponse {

    private int gameId;
    private int playerId;
    private Colour colour;

    public InitialResponse(int gameId, int playerId, Colour colour) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

 */

/*
package ie.gmit.sw.controllers;

/*
temporary test class - to be deleted.

public class Message {
    private String name;
    private int id;

    public Message() {

    }

    Message(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void inc() {
        id++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
 */