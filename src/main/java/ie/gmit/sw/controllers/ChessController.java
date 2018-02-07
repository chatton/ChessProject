package ie.gmit.sw.controllers;

import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.MoveRequest;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.services.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chess/v1/")
public class ChessController {

    private ChessService chessService;

    @Autowired
    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("newgame")
    public NewGameResponse newGameResponse() {
        return chessService.newGame();
    }

    @GetMapping("gamestate")
    public GameState getGameState(@RequestParam("gameId") int gameId) {
        return chessService.getGameState(gameId);
    }

    @PostMapping("makemove")
    public void makeMove(@RequestBody MoveRequest moveRequest) {
        chessService.makeMove(moveRequest.getMove(), moveRequest.getGameId());
    }
}



