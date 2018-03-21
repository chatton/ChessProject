import React from "react";
import axios from "axios"; // for http requests

export default class SurrenderButton extends React.Component {

    surrender = () => {
        axios.get("/chess/v1/surrender?playerId=" + this.props.playerId + "&gameId=" + this.props.currentGameId)
            .then(resp => {
                this.props.setCurrentGameId(undefined);
            })
    }

    render(){

        if(this.props.currentGameId === undefined || !this.props.loggedIn){
            return <div/>
        }
        return (
            <div>
                <button className="btn btn-danger btn-block" onClick={this.surrender}>Surrender</button>
            </div>
        )
    }
}