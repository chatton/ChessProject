import React from "react";

export default class InfoComponent extends React.Component {

    renderPlayerInfo = () => {
        const { loggedIn, playerColour } = this.props;
        
        if (!loggedIn || !playerColour) {
            return <div />
        }
        const colour = playerColour.charAt(0) + playerColour.substring(1, playerColour.length).toLowerCase(); // "White"
        return (
            <h4 className="text-info card-text">You are the <strong>{colour}</strong> player</h4>
        );
    }

    renderUserName = () => {
        const { loggedIn, playerName } = this.props;

        if (loggedIn) {
            return (
                <h4 className="text-success card-title">
                    <strong>Success!</strong> You are Logged in as <strong>{playerName}</strong>.
                </h4>
            );
        }
        return <div />
    }

    renderGameStatus = () => {
        const { gameStatus, yourTurn } = this.props;

        if (!gameStatus) {
            return <div />
        }

        switch (gameStatus) {
            case "WAITING":
                return (
                    <h4 className="text-warning card-text">Waiting for another player to join...</h4>
                );
            case "ONGOING": {
                if (yourTurn === undefined) {
                    return (
                        <h4 className="text-info card-text">Opponent joined!</h4>
                    );
                } else if (yourTurn) {
                    return (
                        <h4 className="text-success card-text">It's your turn!</h4>
                    );
                } else {
                    return (
                        <h4 className="text-warning card-text">It's your opponent's turn.</h4>
                    );
                }
            }
            case "FINISHED": {
                const classes = yourTurn ? "card-text text-warning" : "card-text text-success";
                return(
                    <h4 className={classes}>
                            {yourTurn ? "You lost, better luck next time!" : "Contratulations, You won!"}
                    </h4>
                );
            }
        }
    }

    renderGameId = () => {
        const { currentGameId : id } = this.props; 
        return id && <h3 className="card-header text-white">Game ID: {id}</h3>
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