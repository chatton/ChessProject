import React from "react";
import axios from "axios";

export default class BotButton extends React.Component {

    computerGame = () => {
        const { playerId, setCurrentGameId, setPlayerColour } = this.props;
        axios.get(`/chess/v1/computergame?playerId=${playerId}`)
            .then(resp => {
                const { gameId, colour} = resp.data;
                setCurrentGameId(gameId);
                setPlayerColour(colour);
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