import React from "react";
import axios from "axios"; // for http requests

export default class SurrenderButton extends React.Component {

    surrender = () => {
        const { playerId, currentGameId, setCurrentGameId } = this.props;
        axios.get(`/chess/v1/surrender?playerId=${playerId}&gameId=${currentGameId}`);
    }

    render(){
        const { currentGameId, loggedIn, gameStatus, yourTurn } = this.props;

        if(!currentGameId || !loggedIn){
            return <div/>
        }

        const canSurrender = gameStatus === "ONGOING" && yourTurn;

        return (
            <div>
                <button className="btn btn-danger btn-block" disabled={!canSurrender} onClick={this.surrender}>Surrender</button>
            </div>
        )
    }
}