package ie.gmit.sw.controllers;

import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.services.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    private ChessService chessService;

    @Autowired
    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("chess/v1/newgame")
    public NewGameResponse newGameResponse(){
        return chessService.newGame();
    }

    @GetMapping("chess/v1/gamestate")
    public GameState getGameState(@RequestParam("gameId") int gameId) {
        return chessService.getGameState(gameId);
    }


}
