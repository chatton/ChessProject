import React from "react";

export default class InfoComponent extends React.Component {

    constructor(props){
        super(props);
        this.renderGameStatus = this.renderGameStatus.bind(this);
    }

    renderGameStatus(){
        if(!this.props.gameStatus){
            return <div/>
        }
        switch(this.props.gameStatus){
            case "WAITING":
                return <div>Waiting for other player to join.</div>
            case "ONGOING": {
                if(this.props.yourTurn === undefined){
                    return <div>Opponent joined!</div>
                } else if(this.props.yourTurn) {
                    return <div>It's your turn!</div>
                } else{
                    return <div>It's your opponent's turn.</div>
                }
            }
        }
    }

    render() {
        return (
            <div>
                {this.props.loggedIn && this.props.playerColour && <h1>You are the {this.props.playerColour} Player</h1>}
                {this.renderGameStatus()}
            </div>
        );
    }
}
