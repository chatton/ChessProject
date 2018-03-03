import React from "react";
import Form from "./Form";
import NewGameButton from "./NewGameButton";
import InfoComponent from "./InfoComponent";
import CanvasWindow from "./CanvasWindow";
import HeaderComponent from "./HeaderComponent";
import GameListComponent from "./GameListComponent";

export default class ChessApp extends React.Component {

    state = {
        playerId: undefined,
        playerColour: undefined,
        loggedIn: false,
        currentGameId: undefined,
        gameStatus : undefined,
        yourTurn : undefined,
        ongoingGames : [],
        playerName : undefined
    }
    
    setPlayerName = name => {
        this.setState(() => ({playerName:name}));
    }

    setOngoingGames = games => {
        this.setState(() => ({ongoingGames: games}));
    }

    /*
     updates the player id, this is called by the FormComponent
     in order to pass it down as props to the NewGameButtonComponent
     */
    setPlayerId = id => {
        this.setState(() => ({playerId: id}));
    }

    setPlayerColour = colour => {
        this.setState(() => ({playerColour: colour}));
    }

    setLoggedIn = value => {
        this.setState(() => ({loggedIn: value}));
    }

    setCurrentGameId = id => {
        this.setState(() => ({currentGameId: id}));
    }

    setGameStatus = status => {
        this.setState(()=>({gameStatus : status}));
    }

    setYourTurn = value => {
        this.setState(()=>({yourTurn : value}));
    }

    render() {
        return (
            <div className="container">
                <HeaderComponent title="Chess Online" loggedIn={this.state.loggedIn}/>
                <Form 
                    updateId={this.setPlayerId} 
                    setLoggedIn={this.setLoggedIn} 
                    loggedIn={this.state.loggedIn}
                    setOngoingGames={this.setOngoingGames}
                    setPlayerName={this.setPlayerName}
                />
                <NewGameButton
                    playerId={this.state.playerId}
                    setPlayerColour={this.setPlayerColour}
                    setCurrentGameId={this.setCurrentGameId}
                    loggedIn={this.state.loggedIn}
                    setOngoingGames={this.setOngoingGames}
                />

                <InfoComponent 
                    gameStatus={this.state.gameStatus}
                    playerColour={this.state.playerColour} 
                    loggedIn={this.state.loggedIn}
                    yourTurn={this.state.yourTurn}
                />

                <div className="row">
                    <div className="col-md-8 col-lg-8 col-sm-8">
                        <CanvasWindow
                            size={8}
                            squareSize={88}
                            currentGameId={this.state.currentGameId}
                            loggedIn={this.state.loggedIn}
                            playerId={this.state.playerId}
                            setGameStatus={this.setGameStatus}
                            setYourTurn={this.setYourTurn}
                        />
                    </div>
                    <div className="col-md-4 col-lg-4 col-sm-4">
                        <GameListComponent 
                            loggedIn={this.state.loggedIn}
                            onGoingGames={this.state.ongoingGames}
                            setCurrentGameId={this.setCurrentGameId}
                            playerName={this.state.playerName}
                            playerId={this.state.playerId}
                            setOngoingGames={this.setOngoingGames}
                        />
                    </div>
                </div>
            </div>
        );
    }
}
