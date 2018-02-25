import React from "react";
import Form from "./Form";
import NewGameButton from "./NewGameButton";
import PlayerColour from "./PlayerColour";
import CanvasWindow from "./CanvasWindow";

export default class ChessApp extends React.Component {

    constructor(props) {
        super(props);
        this.setPlayerId = this.setPlayerId.bind(this);
        this.setPlayerColour = this.setPlayerColour.bind(this);
        this.setLoggedIn = this.setLoggedIn.bind(this);
        this.setCurrentGameId = this.setCurrentGameId.bind(this);

        this.state = {
            playerId: undefined,
            playerColour: undefined,
            loggedIn: false,
            currentGameId: undefined
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


    render() {
        return (
            <div>
                <Form updateId={this.setPlayerId} setLoggedIn={this.setLoggedIn}/>
                <NewGameButton
                    playerId={this.state.playerId}
                    setPlayerColour={this.setPlayerColour}
                    setCurrentGameId={this.setCurrentGameId}
                />

                <PlayerColour playerColour={this.state.playerColour} loggedIn={this.state.loggedIn}/>

                <CanvasWindow
                    size={8}
                    squareSize={64}
                    currentGameId={this.state.currentGameId}
                    loggedIn={this.state.loggedIn}
                    playerId={this.state.playerId}
                />
            </div>
        );
    }
}
