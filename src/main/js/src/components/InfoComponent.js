import React from "react";

export default class InfoComponent extends React.Component {

    renderPlayerInfo = () => {
        if (!this.props.loggedIn || !this.props.playerColour) {
            return <div />
        }
        const colour = this.props.playerColour; // "WHITE"
        const playerColour = colour.charAt(0) + colour.substring(1, colour.length).toLowerCase(); // "White"
        return (
            <h4 className="text-info card-text">You are the <strong>{playerColour}</strong> player</h4>
        );
    }

    renderUserName = () => {
        if (this.props.loggedIn) {
            return (
                <h4 className="text-success card-title">
                    <strong>Success!</strong> You are Logged in as <strong>{this.props.playerName}</strong>.
                </h4>
            );
        }
        return <div />
    }

    renderGameStatus = () => {
        if (!this.props.gameStatus) {
            return <div />
        }

        switch (this.props.gameStatus) {
            case "WAITING":
                return (
                    <h4 className="text-warning card-text">Waiting for another player to join...</h4>
                );
            case "ONGOING": {
                if (this.props.yourTurn === undefined) {
                    return (
                        <h4 className="text-info card-text">Opponent joined!</h4>
                    );
                } else if (this.props.yourTurn) {
                    return (
                        <h4 className="text-success card-text">It's your turn!</h4>
                    );
                } else {
                    return (
                        <h4 className="text-warning card-text">It's your opponent's turn.</h4>

                    );
                }
            }
        }
    }

    renderGameId = () => {
        return this.props.currentGameId && <h3 className="card-header text-white">Game ID: {this.props.currentGameId}</h3>
    }

    render() {

        const anim = !this.props.loggedIn ? "fade-anim hide" : "fade-anim"
        return (
            <div className="container">
                <div className={anim + " card bg-dark"}>
                    <div className="card-body">
                        {this.renderGameId()}
                        {this.renderUserName()}
                        {this.renderPlayerInfo()}
                        {this.renderGameStatus()}
                    </div>
                </div>
            </div>
        );
    }
}