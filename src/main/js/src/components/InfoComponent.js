import React from "react";
import _ from "lodash";

export default class InfoComponent extends React.Component {


    renderPlayerInfo = () => {
        if(!this.props.loggedIn || !this.props.playerColour){
            return <div/>
        }
        const colour = this.props.playerColour; // "WHITE"
        const playerColour = colour.charAt(0) + colour.substring(1, colour.length).toLowerCase(); // "White" 
        // const colour = _.startCase(_.toLower(this.props.playerColour)); // "WHITE" -> "White"
        return (
            <div>
                <p className="alert alert-info">You are the <strong>{playerColour}</strong> player</p>
            </div>
        );
    }

    renderUserName = () => {
        if (this.props.loggedIn) {
            return(
                <div className="alert alert-success">
                    <strong>Success!</strong> You are Logged in as <strong>{this.props.playerName}</strong>.
                </div>
            );
        }
        return <div/>
    }

    renderGameStatus = () => {
        if(!this.props.gameStatus){
            return <div/>
        }
        
        switch(this.props.gameStatus){
            case "WAITING":
                return (
                    <div>
                        <p className="alert alert-info">Waiting for another player to join...</p>
                    </div>
                );
            case "ONGOING": {
                if(this.props.yourTurn === undefined){
                    return (
                        <div>
                            <p className="alert alert-info">Opponent joined!</p>
                        </div>
                    );
                } else if(this.props.yourTurn) {
                    return (
                        <div>
                            <p className="alert alert-success">It's your turn!</p>
                        </div>
                    );
                } else{
                    return (
                        <div>
                            <p className="alert alert-warning">It's your opponent's turn.</p>
                        </div>
                    );
                }
            }
        }
    }

    render() {

        const classes = !this.props.loggedIn ? "container fade-anim hide" : "container fade-anim"
        return (
           
            <div className={classes}>
                {this.renderUserName()}
                {this.renderPlayerInfo()}
                {this.renderGameStatus()}
            </div>
        );
    }
}
