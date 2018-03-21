import React from "react";
import axios from "axios";

export default class BotButton extends React.Component {

    computerGame = () => {
        axios.get("/chess/v1/computergame?playerId=" + this.props.playerId)
            .then(resp => {
                // this.props.setOngoingGames(resp.data);
                this.props.setCurrentGameId(resp.data.gameId);
                this.props.setPlayerColour(resp.data.colour);
            })
            .catch(err => {
                console.log(err);
            })
    }

    render() {
        const classes = !this.props.loggedIn
            ? "container fade-anim hide"
            : "container fade-anim";

        return (
            <div className={classes}>
                <button className="btn-primary btn btn-block" onClick={this.computerGame}>Vs. Computer</button>
            </div>
        );
    }
}