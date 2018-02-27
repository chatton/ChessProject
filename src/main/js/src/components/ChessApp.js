import React from "react";
import Form from "./Form";
import NewGameButton from "./NewGameButton";
import InfoComponent from "./InfoComponent";
import CanvasWindow from "./CanvasWindow";
import HeaderComponent from "./HeaderComponent";
import GameListComponent from "./GameListComponent";

export default class ChessApp extends React.Component {

    constructor(props) {
        super(props);
        this.setPlayerId = this.setPlayerId.bind(this);
        this.setPlayerColour = this.setPlayerColour.bind(this);
        this.setLoggedIn = this.setLoggedIn.bind(this);
        this.setCurrentGameId = this.setCurrentGameId.bind(this);
        this.setGameStatus = this.setGameStatus.bind(this);
        this.setYourTurn = this.setYourTurn.bind(this);

        this.state = {
            playerId: undefined,
            playerColour: undefined,
            loggedIn: false,
            currentGameId: undefined,
            gameStatus : undefined,
            yourTurn : undefined
        }
    }

    /*
     updates the player id, this is called by the FormComponent
     in order to pass it down as props to the NewGameButtonComponent
     */
    setPlayerId(id) {
        this.setState(() => ({playerId: id}));
    }

    setPlayerColour(colour) {
        this.setState(() => ({playerColour: colour}));
    }

    setLoggedIn(value) {
        this.setState(() => ({loggedIn: value}));
    }

    setCurrentGameId(id) {
        this.setState(() => ({currentGameId: id}));
    }

    setGameStatus(status){
        this.setState(()=>({gameStatus : status}));
    }

    setYourTurn(value){
        this.setState(()=>({yourTurn : value}));
    }

    render() {
        return (
            <div className="container">
                <HeaderComponent title="Chess Online"/>
                <Form 
                    updateId={this.setPlayerId} 
                    setLoggedIn={this.setLoggedIn} 
                    loggedIn={this.state.loggedIn}
                />
                <NewGameButton
                    playerId={this.state.playerId}
                    setPlayerColour={this.setPlayerColour}
                    setCurrentGameId={this.setCurrentGameId}
                    loggedIn={this.state.loggedIn}
                />

             
                <div className="row">
                    <div className="col-md-9 col-lg-9 col-sm-9">
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
                    <div className="col-md-3 col-lg-3 col-sm-3">
                        <GameListComponent loggedIn={this.state.loggedIn}/>
                    </div>
                </div>

                <InfoComponent 
                    gameStatus={this.state.gameStatus}
                    playerColour={this.state.playerColour} 
                    loggedIn={this.state.loggedIn}
                    yourTurn={this.state.yourTurn}
                />
            </div>
        );
    }
}
