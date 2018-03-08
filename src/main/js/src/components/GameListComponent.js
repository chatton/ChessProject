import React from "react";
import GameComponent from "./GameComponent";
import axios from "axios";

export default class GameListComponent extends React.Component {
    
   
    tick = () => {
        axios.get("/chess/v1/allgames/?playerId=" + this.props.playerId)
            .then(resp => {
                this.props.setOngoingGames(resp.data)
            })
            .catch(err => {
                console.log(err);
            })
    }
      
    // when the component is unmounted, stop updating.
    componentWillUnmount() {
        clearInterval(this.interval);
    }
    
    // keep updating the game list so the turn info is displayed in real time.
    componentDidMount() {
        this.tick();
        this.interval = setInterval(this.tick, 5000);
    }

    /*
    determine the message that should be displayed based on the state
    of the game and who the opponent is.
    */
    getMessage = game => {
        const myName = this.props.playerName;
        if(myName === game.whitePlayerName){
            if(!game.blackPlayerName){
                return <h4 className="alert alert-info">Waiting for black player to join.</h4>;
            }

            let name = game.blackPlayerName;
            if(name.length > 20){
                name = name.substring(0, 20) + "...";
            }
            if(game.currentTurn === "BLACK"){
                return <h4 className="alert alert-warning">Waiting for <strong>{name}</strong> to make their move.</h4>;
            } else {
                return <h4 className="alert alert-success">It's your turn against <strong>{name}</strong>!</h4>;
            }
            
        } else {
            if(!game.whitePlayerName){
                return <h4 className="alert alert-info">Waiting for white player to join."</h4>;
            }

            let name = game.whitePlayerName;
            if(name.length > 20){
                name = name.substring(0, 20) + "...";
            }

            if(game.currentTurn === "WHITE"){
                return <h4 className="alert alert-warning">Waiting for <strong>{name}</strong> to make their move.</h4>;
            } else {
                return <h4 className="alert alert-success">It's your turn agains <strong>{name}</strong>!</h4>;
            }

        }
    }


    render(){
        if(!this.props.loggedIn){
            return <div/>
        }
        return (
            <div>
                <h2 className="page-header text-white">Ongoing Games</h2>
                <ul className="list-group">
                    {this.props.onGoingGames.map(gameInfo => {
                        // don't display games that are finished.
                        if(gameInfo.gameStatus === "FINISHED"){
                            return <div/>
                        }
                        return (
                            <li key={gameInfo.gameId} className="list-group-item">
                                <div>
                                  {this.getMessage(gameInfo)}
                                     <button className="btn btn-info" onClick={() => this.props.setCurrentGameId(gameInfo.gameId)}>Switch to Game</button>
                                </div>
                            </li>
                        );
                    })}
                </ul>
            </div>
        );
    }
}

