import React from "react";
import axios from "axios"; // for http requests

export default class NewGameButton extends React.Component {
    requestNewPublicGame = () => {
        const id = this.props.playerId;
        axios.get("/chess/v1/newgame?playerId=" + id)
            .then(response => {
                const data = response.data;
                this.props.setPlayerColour(data.colour);
                this.props.setCurrentGameId(data.gameId);

                axios.get("/chess/v1/allgames/?playerId=" + id)
                    .then(resp => {
                        // save the ongoing games to be visible in the GameListComponent
                        this.props.setOngoingGames(resp.data);
                    })
                    .catch(err => {
                        console.log(err);
                    });
            })
            .catch(err => {
                console.log(err);
            });
    };


    render() {
        const classes = !this.props.loggedIn
            ? "container fade-anim hide"
            : "container fade-anim";

        return (
            <div className={classes}>
                {this.props.playerId !== undefined && (
                    <button
                        type="button"
                        className="btn btn-success btn-block"
                        onClick={this.requestNewPublicGame}
                    >
                        New Game
                    </button>
                )}
            </div>
        );
    }
}