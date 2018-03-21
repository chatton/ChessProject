package ie.gmit.sw.controllers;

import ie.gmit.sw.model.*;
import ie.gmit.sw.services.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("chess/v1/")
public class ChessController {

    private ChessService chessService;

    @Autowired
    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("newgame")
    public NewGameResponse newGameResponse(
            @RequestParam("playerId") int playerId,
            @RequestParam(name = "private", required = false) boolean isPrivate) {
        return chessService.newGame(playerId, isPrivate);
    }

    @GetMapping("joinprivategame")
    public NewGameResponse joinPrivateGame(
            @RequestParam("gameId") int gameId,
            @RequestParam("playerId") int playerId
    ) {
        return chessService.joinPrivateGame(gameId, playerId);
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

    @PostMapping("login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return chessService.login(request);
    }

    @GetMapping("allgames")
    public List<GameInfo> allGames(@RequestParam("playerId") int playerId) {
        return chessService.getAllGames(playerId);
    }

    @GetMapping("computergame")
    public NewGameResponse computerGame(@RequestParam("playerId") int playerId) throws IOException{
        return chessService.newComputerGame(playerId);
    }
    @GetMapping("surrender")
    public void surrenderGame(
            @RequestParam("playerId") int playerId,
            @RequestParam("gameId") int gameId
    ){
        chessService.surrenderGame(playerId, gameId);
    }
}