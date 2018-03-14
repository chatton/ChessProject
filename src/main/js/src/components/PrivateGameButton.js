import React from "react";
import axios from "axios"; // for http requests

export default class PrivateGameButton extends React.Component {

    requestNewPrivateGame = () => {
        const id = this.props.playerId;
        axios.get("/chess/v1/newgame?playerId=" + id + "&private=true")
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
    }

    joinExistingPrivateGame = () => {
        const playerId = this.props.playerId;
        const gameId = this.refs.gameIdForm.elements.gameId.value.trim();
        this.refs.gameIdForm.elements.gameId.value = "";
        axios.get("/chess/v1/joinprivategame?playerId=" + playerId + "&gameId=" + gameId)
            .then(resp => {
                const data = resp.data;
                this.props.setCurrentGameId(data.gameId);
                this.props.setPlayerColour(data.colour);

                axios.get("/chess/v1/allgames/?playerId=" + playerId)
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
    }

    renderButton = () => {
        if (!this.refs.gameIdForm) {
            return (
                <button
                    type="button"
                    className="btn btn-info btn-block"
                    onClick={this.requestNewPrivateGame}>
                    Create Private Game
                </button>
            );
        }
        const idField = this.refs.gameIdForm.elements.gameId;
        const typedId = idField.value.trim();

        let willCreateNewGame = false;
        let text;
        let clickEvent;
        if (typedId === "") {
            text = "Create Private Game";
            willCreateNewGame = true;
            clickEvent = this.requestNewPrivateGame;
        } else {
            text = "Join Private Game";
            clickEvent = this.joinExistingPrivateGame;
        }

        let classes = willCreateNewGame ? "btn-info" : "btn-danger";
        classes += " btn btn-block";

        return (
            <button
                type="button"
                className={classes}
                onClick={clickEvent}>
                {text}
            </button>
        );
    }

    render() {
        const classes = !this.props.loggedIn
            ? "container fade-anim hide"
            : "container fade-anim";
        return (
            <div className={classes}>
                {this.props.playerId !== undefined && (
                    <div>
                        {this.renderButton()}
                        <form ref="gameIdForm">
                            <div className="form-group">
                                <input className="form-control" name="gameId" id="gameId" type="text" placeholder="Enter game id"></input>
                            </div>
                        </form>

                    </div>
                )}
            </div>
        );
    }
}