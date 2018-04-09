import React from "react";
import GameComponent from "./GameComponent";
import axios from "axios";

export default class GameListComponent extends React.Component {
    tick = () => {
        if (!this.props.playerId) {
            return;
        }
        axios.get("/chess/v1/allgames/?playerId=" + this.props.playerId)
            .then(resp => {
                this.props.setOngoingGames(resp.data);
            })
            .catch(err => {
                console.log(err);
            });
    };

    // when the component is unmounted, stop updating.
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    // keep updating the game list so the turn info is displayed in real time.
    componentDidMount() {
        this.tick();
        this.interval = setInterval(this.tick, 2000);
    }


    render() {
        if (!this.props.loggedIn) {
            return <div />;
        }
        return (
            <div className="game-wrapper">

                <ul>
                    {this.props.onGoingGames.map(gameInfo => {
                        // don't display games that are finished.
                        if (gameInfo.gameStatus === "FINISHED") {
                            return <div />;
                        }
                        return (
                            <li key={gameInfo.gameId} className="list-wrapper">
                                <GameComponent
                                    setCurrentGameId={this.props.setCurrentGameId}
                                    playerName={this.props.playerName}
                                    gameInfo={gameInfo}
                                />
                                <br /> {/* make it so each list element isn't stacked on top of each other.*/}
                            </li>
                        );
                    })}
                </ul>
            </div>
        );
    }
}


// <div className="game-card">
// <div className="game-card game-card-front">
//         {this.getMessage(gameInfo)}                      
// </div>
// <div className="game-card game-card-back">
//     <button
//         className="btn btn-info fill"
//         onClick={() => this.props.setCurrentGameId(gameInfo.gameId)}>
//             {this.getButtonText()}
//     </button>
// </div>
// </div>


// <h1 className="page-header text-white text-center outline mb-5">
// Ongoing Games
// </h1>

// <li key={gameInfo.gameId} className="list-group-item bg-dark">
// <div className="bg-dark">
//     {this.getMessage(gameInfo)}
//     <button
//         className="btn btn-outline-info btn-block mb-2"
//         onClick={() => this.props.setCurrentGameId(gameInfo.gameId)}
//     >
//         Switch to Game
//     </button>
// </div>
// </li>


// const myName = this.props.playerName;
// if (myName === game.whitePlayerName) {
//     if (!game.blackPlayerName) {
//         return <h3 className="text-info">Waiting for black player to join.</h3>;
//     }

//     let name = game.blackPlayerName;
//     if (name.length > 20) {
//         name = name.substring(0, 20) + "...";
//     }
//     if (game.currentTurn === "BLACK") {
//         return (
//             <h3 className="text-warning">
//                 Waiting for <strong>{name}</strong> to make their move.
//             </h3>
//         );
//     } else {
//         return (
//             <h3 className="text-success">
//                 It's your turn against <strong>{name}</strong>!
//             </h3>
//         );
//     }
// } else {
//     if (!game.whitePlayerName) {
//         return (
//             <h3 className="text-info">Waiting for white player to join."</h3>
//         );
//     }

//     let name = game.whitePlayerName;
//     if (name.length > 20) {
//         name = name.substring(0, 20) + "...";
//     }

//     if (game.currentTurn === "WHITE") {
//         return (
//             <h3 className="text-warning">
//                 Waiting for <strong>{name}</strong> to make their move.
//             </h3>
//         );
//     } else {
//         return (
//             <h3 className="text-success">
//                 It's your turn against <strong>{name}</strong>!
//             </h3>
//         );
//     }
// }