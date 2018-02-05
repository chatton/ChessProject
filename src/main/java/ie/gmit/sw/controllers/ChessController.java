package ie.gmit.sw.controllers;

import ie.gmit.sw.chess.board.ChessBoard;
import ie.gmit.sw.chess.board.ChessFactory;
import ie.gmit.sw.chess.board.pieces.Colour;
import ie.gmit.sw.chess.board.pieces.King;
import ie.gmit.sw.model.GameState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {


    @RequestMapping("/chess/v1/gamestate")
    public ResponseEntity<GameState> getGameState() {
        ChessBoard board = ChessFactory.newStandardChessBoard();
        board.setAt("B5", new King(board, Colour.WHITE));
        return ResponseEntity.ok(new GameState(board));
    }


    // /chess/v1/newgame
    // give back the new game's id and if it's ready or not


    // /chess/v1/get-game-state/id=12345
    // gives back what the board looks like

    // /chess/v1/make-move?id=12345&from=A1&to=A2
    // makes the move if it is valid, otherwise returns error code.


}
