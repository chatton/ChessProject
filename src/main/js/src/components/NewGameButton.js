import React from "react";
import axios from "axios";

export default class NewGameButton extends React.Component {

    constructor(props) {
        super(props);
        this.requestNewGame = this.requestNewGame.bind(this);
    }

    requestNewGame() {
        const id = this.props.playerId;
        axios.get("/chess/v1/newgame?playerId=" + id)
            .then(response => {
                const data = response.data;
                console.log(data);
                this.props.setPlayerColour(data.colour);
                console.log("setting game id as: " + data.gameId);
                this.props.setCurrentGameId(data.gameId);
            });

    }

    render() {
        return (
            <div>
                {this.props.playerId !== undefined &&
                <button className="btn-success" onClick={this.requestNewGame}>New Game</button>}
            </div>
        );
    }
}
