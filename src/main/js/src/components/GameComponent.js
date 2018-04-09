import React from "react";

export default class GameComponent extends React.Component {


    /*
      determine the message that should be displayed based on the state
      of the game and who the opponent is.
      */
    getMessage = () => {
        const { gameInfo: game, playerName: myName } = this.props;
        if (myName === game.whitePlayerName) {
            if (!game.blackPlayerName) {
                return <div className="waiting my-container"><h3 className="center">Waiting...</h3></div>;
            }

            let name = game.blackPlayerName;
            if (name.length > 20) {
                name = name.substring(0, 20) + "...";
            }
            if (game.currentTurn === "BLACK") {
                return (
                    <div className="their-turn my-container">
                        <div className="padding-top-small">
                            <h3 className="margin-bottom-small">
                                It's their turn!
                            </h3>
                            <br />
                            <div className="my-container">
                                <h3 className="vert">
                                    Vs. <strong>{name}</strong>
                                </h3>
                            </div>
                        </div>
                    </div>
                );
            } else {
                return (
                    <div className="your-turn my-container">
                        <div className="padding-top-small">
                            <h3 className="margin-bottom-small">
                                It's your turn!
                            </h3>
                            <br />
                            <div className="my-container">
                                <h3 className="vert">
                                    Vs. <strong>{name}</strong>
                                </h3>
                            </div>
                        </div>
                    </div>
                );
            }
        } else {
            if (!game.whitePlayerName) {
                return (
                    <div className="waiting my-container"><h3 className="center">Waiting...</h3></div>
                );
            }

            let name = game.whitePlayerName;
            if (name.length > 20) {
                name = name.substring(0, 20) + "...";
            }

            if (game.currentTurn === "WHITE") {
                return (
                    <div className="their-turn my-container">
                        <div className="padding-top-small">
                            <h3 className="margin-bottom-small">
                                It's their turn!
                            </h3>
                            <br />
                            <div className="my-container">
                                <h3 className="vert">
                                    Vs. <strong>{name}</strong>
                                </h3>
                            </div>
                        </div>
                    </div>
                );
            } else {
                return (
                    <div className="your-turn my-container">
                        <div className="padding-top-small">
                            <h3 className="margin-bottom-small">
                                It's your turn!
                            </h3>
                            <br />
                            <div className="my-container">
                                <h3 className="vert">
                                    Vs. <strong>{name}</strong>
                                </h3>
                            </div>
                        </div>
                    </div>
                );
            }
        }
    }


    getButtonText = () => {
        const { whitePlayerName, currentTurn, blackPlayerName, gameStatus } = this.props.gameInfo;
        const playerName = this.props.playerName;
        let colour;
        if (whitePlayerName === playerName) {
            colour = "WHITE";
        } else {
            colour = "BLACK";
        }
        if (colour === currentTurn && gameStatus === "ONGOING") {
            return (

                <button className="btn fill btn-info center">
                    <h3>Make your move</h3>
                </button>

            );
        }

        if (blackPlayerName === null || blackPlayerName === undefined) {
            return (
                <button className="btn fill btn-info center">
                    <h3>Switch to Game</h3>
                </button>
            )
        }

        return (
            <button className="btn fill btn-info center">
                <h3>Switch to Game</h3>
            </button>
        );



    }

    render() {
        return (
            <div>
                <div className="game-card">
                    <div className="game-card game-card-front">
                        {this.getMessage()}
                    </div>
                    <div className="game-card game-card-back">
                        <button
                            className="btn btn-info fill"
                            onClick={() => this.props.setCurrentGameId(this.props.gameInfo.gameId)}>
                            {this.getButtonText()}
                        </button>
                    </div>
                </div>
            </div>
        );
    }

}

