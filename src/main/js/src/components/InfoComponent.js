import React from "react";

export default class InfoComponent extends React.Component {


    renderPlayerInfo = () => {
        if(!this.props.loggedIn || !this.props.playerColour){
            return <div/>
        }
        const colour = this.props.playerColour; // "WHITE"
        const playerColour = colour.charAt(0) + colour.substring(1, colour.length).toLowerCase(); // "White" 
        return (
            <div>
                <p className="alert alert-info">You are the <strong>{playerColour}</strong> player</p>
            </div>
        );
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

        if(!this.props.loggedIn){
            return <div/>
        }

        return (
           
            <div>
                {this.renderPlayerInfo()}
                {this.renderGameStatus()}
            </div>
        );
    }
}
