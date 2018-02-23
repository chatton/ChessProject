package ie.gmit.sw.controllers;

import ie.gmit.sw.model.GameState;
import ie.gmit.sw.model.MoveRequest;
import ie.gmit.sw.model.NewGameResponse;
import ie.gmit.sw.model.RegisterRequest;
import ie.gmit.sw.model.RegistrationResponse;
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
    public NewGameResponse newGameResponse(@RequestParam("playerId") int playerId) {
        return chessService.newGame(playerId);
    }

    @GetMapping("gamestate")
    public GameState getGameState(
            @RequestParam("gameId") int gameId,
            @RequestParam("playerId") int playerId
    ) {
        return chessService.getGameState(gameId, playerId);
    }

    @PostMapping("makemove")
    public void makeMove(@RequestBody MoveRequest moveRequest) {
        chessService.makeMove(moveRequest.getMove(), moveRequest.getGameId(), moveRequest.getPlayerId());
    }

    @PostMapping("register")
    public RegistrationResponse registerUser(@RequestBody RegisterRequest request) {
        return chessService.register(request);
    }
}



