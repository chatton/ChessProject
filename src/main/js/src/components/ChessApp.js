import React from "react";
import Form from "./Form";
import NewGameButton from "./NewGameButton";
import InfoComponent from "./InfoComponent";
import CanvasWindow from "./CanvasWindow";
import HeaderComponent from "./HeaderComponent";
import GameListComponent from "./GameListComponent";
import LogoutButton from "./LogoutButton";
import PrivateGameButton from "./PrivateGameButton";
import BotButton from "./BotButton";
import SurrenderButton from "./SurrenderButton";

export default class ChessApp extends React.Component {

    state = {
        playerId: undefined,
        playerColour: undefined,
        loggedIn: false,
        currentGameId: undefined,
        gameStatus: undefined,
        yourTurn: undefined,
        ongoingGames: [],
        playerName: undefined
    };

    setPlayerName = name => {
        this.setState(() => ({playerName: name}));
    };

    setOngoingGames = games => {
        this.setState(() => ({ongoingGames: games}));
    };

    /*
     updates the player id, this is called by the FormComponent
     in order to pass it down as props to the NewGameButtonComponent
     */
    setPlayerId = id => {
        this.setState(() => ({playerId: id}));
    };

    setPlayerColour = colour => {
        this.setState(() => ({playerColour: colour}));
    };

    setLoggedIn = value => {
        this.setState(() => ({loggedIn: value}));
    };

    setCurrentGameId = id => {
        this.setState(() => ({currentGameId: id}));
        this.poll();
    };

    setGameStatus = status => {
        this.setState(() => ({gameStatus: status}));
    };

    setYourTurn = value => {
        this.setState(() => ({yourTurn: value}));
    };

    verticalPadding = () => {

        //https://stackoverflow.com/questions/12273588/add-vertical-blank-space-using-twitter-bootstrap

        //this div exists purely to add some spacing between the header and the form.

        return <div className="form-group">&nbsp;</div>;
    };

    render() {
        return (
            <div>
                <div className="container">
                    <HeaderComponent
                        title="Chess Online"
                        loggedIn={this.state.loggedIn}
                    />
                    {this.verticalPadding()}

                    <Form
                        updateId={this.setPlayerId}
                        setLoggedIn={this.setLoggedIn}
                        loggedIn={this.state.loggedIn}
                        setOngoingGames={this.setOngoingGames}
                        setPlayerName={this.setPlayerName}
                    />

                    {this.verticalPadding()}

                    <div
                        className={
                            this.state.loggedIn ? "row slide-right" : "row slide-right back"
                        }
                    >
                        <div className="col-md-8 col-lg-8 col-sm-12 col-12">
                            <div className="row">

                                <div className="col-md-6 col-lg-6 col-sm-12 col-12">
                                    <LogoutButton
                                        loggedIn={this.state.loggedIn}
                                        setLoggedIn={this.setLoggedIn}
                                    />
                                </div>

                                <div className="col-md-6 col-lg-6 col-sm-12 col-12">
                                    <NewGameButton
                                        playerId={this.state.playerId}
                                        setPlayerColour={this.setPlayerColour}
                                        setCurrentGameId={this.setCurrentGameId}
                                        loggedIn={this.state.loggedIn}
                                        setOngoingGames={this.setOngoingGames}
                                    />
                                </div>

                                <div className="col-md-6 col-lg-6 col-sm-12 col-12 pt-3">
                                    <PrivateGameButton
                                        setPlayerColour={this.setPlayerColour}
                                        setCurrentGameId={this.setCurrentGameId}
                                        setOngoingGames={this.setOngoingGames}
                                        playerId={this.state.playerId}
                                        loggedIn={this.state.loggedIn}
                                    />
                                </div>

                                <div className="col-md-6 col-lg-6 col-sm-12 col-12 pt-3">
                                    <BotButton
                                        playerId={this.state.playerId}

                                        loggedIn={this.state.loggedIn}
                                        setCurrentGameId={this.setCurrentGameId}
                                        setPlayerColour={this.setPlayerColour}
                                    />
                                </div>

                            </div>

                            {this.verticalPadding()}
                            <div>

                                <InfoComponent
                                    playerName={this.state.playerName}
                                    gameStatus={this.state.gameStatus}
                                    playerColour={this.state.playerColour}
                                    loggedIn={this.state.loggedIn}
                                    yourTurn={this.state.yourTurn}
                                    currentGameId={this.state.currentGameId}
                                />

                                <div className="pt-3"/>

                                <CanvasWindow
                                    size={8}
                                    squareSize={88}
                                    currentGameId={this.state.currentGameId}
                                    loggedIn={this.state.loggedIn}
                                    playerId={this.state.playerId}
                                    setGameStatus={this.setGameStatus}
                                    setYourTurn={this.setYourTurn}
                                    setPlayerColour={this.setPlayerColour}
                                />

                                <div className="pt-3"/>

                                <SurrenderButton
                                    currentGameId = {this.state.currentGameId}
                                    loggedIn ={this.state.loggedIn}
                                    playerId = {this.state.playerId}
                                    setCurrentGameId ={this.setCurrentGameId}
                                />
                            </div>

                        </div>

                        <div className="col-md-4 col-lg-4 col-sm-12 col-12">
                            <GameListComponent
                                loggedIn={this.state.loggedIn}
                                onGoingGames={this.state.ongoingGames}
                                setCurrentGameId={this.setCurrentGameId}
                                playerName={this.state.playerName}
                                playerId={this.state.playerId}
                                setOngoingGames={this.setOngoingGames}
                            />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}