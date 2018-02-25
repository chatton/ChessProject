import React from "react";
import axios from 'axios';

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

export default class CanvasWindow extends React.Component {

    constructor(props) {
        super(props);
        this.poll = this.poll.bind(this);
        this.state = {
            gameState: {},
            shouldDraw: false
        }
    }

    shouldPoll(){
        return this.props.currentGameId !== undefined;
    }

    mapToChess(x, y) {
        const asciiCode = 65;
        const mappedInt = this.props.size - y;
        return String.fromCharCode(asciiCode + x) + "" + mappedInt;
    }

    drawCheck(x, y) {
        // if a king is in check, draw a circle around them.
        const check = this.state.gameState.check;
        const blackKingLocation = check["BLACK"]["location"];
        const whiteKingLocation = check["WHITE"]["location"];
        const ctx = this.getCtx();
        ctx.strokeStyle = "red";
        ctx.lineWidth = 3;
        if (this.mapToChess(x, y) === blackKingLocation) {
            const blackInCheck = check["BLACK"]["inCheck"] === "true";
            if (blackInCheck) {
                ctx.beginPath();
                ctx.arc(this.props.squareSize / 2 + x * this.props.squareSize, this.props.squareSize / 2 + y * this.props.squareSize, this.props.squareSize / 2, 0, 2 * Math.PI);
                ctx.stroke();
            }
        } else if (this.mapToChess(x, y) === whiteKingLocation) {
            const whiteInCheck = check["WHITE"]["inCheck"] === "true";
            if (whiteInCheck) {
                ctx.beginPath();
                ctx.arc(this.props.squareSize / 2 + x * this.props.squareSize, this.props.squareSize / 2 + y * this.props.squareSize, this.props.squareSize / 2, 0, 2 * Math.PI);
                ctx.stroke();
            }
        }
    }

    poll() {
        console.log("polling...");
        axios.get("/chess/v1/gamestate?gameId=" + this.props.currentGameId + "&playerId=" + this.props.playerId)
            .then(response => {
                console.log("From server: ");
                console.log(response.data);
                this.setState(() => ({
                    gameState: response.data,
                    shouldDraw: true,
                    selectedTile: undefined,
                    shouldDrawGrid: true
                }));
            });

        setTimeout(this.poll, 2000)
    }

    componentDidMount() {
        this.updateCanvas();
    }

    componentDidUpdate() {
        if(this.shouldPoll()){
            this.poll();
        }
        this.updateCanvas();
    }

    getCtx() {
        const canvas = this.refs.canvas;
        return canvas.getContext("2d");
    }

    drawSquare(colour, x, y) {

        const image = new Image();
        const currentChessPosition = this.mapToChess(x, y); // ex. 0,0 -> A8
        // the name of the image from the response matches the name of the image files.

        const gameOver = this.state.gameState.gameStatus === "FINISHED";
        if (!gameOver && this.state.shouldDrawGrid) { // only draw the board when the game is ongoing.
            image.src = "images/" + this.state.gameState.positions[currentChessPosition] + ".png";
            image.onload = () => {
                const ctx = this.getCtx();
                ctx.fillStyle = colour;
                // if the tile being drawn is the selected tile, draw it yellow instead.
                if (this.mapToChess(x, y) === this.state.selectedTile) {
                    ctx.fillStyle = "yellow";
                }

                // draw a rectangle
                ctx.fillRect(this.props.squareSize * x, this.props.squareSize * y, this.props.squareSize, this.props.squareSize);

                // and then an image on top of it.
                ctx.drawImage(image, this.props.squareSize * x, this.props.squareSize * y, this.props.squareSize, this.props.squareSize);

                this.drawCheck(x, y);

                if (gameOver) {
                    this.setState(() => ({shouldDrawGrid: false}));
                }
            };
        }
    }

    updateCanvas() {
        console.log("Updating canvas");
        for (let x = 0; x < this.props.size; x++) {
            for (let y = 0; y < this.props.size; y++) {
                console.log("drawing square");
                if (isWhiteSquare(x, y)) {
                    this.drawSquare("#ffec96", x, y);
                }
                else {
                    this.drawSquare("#403f49", x, y)
                }
            }
        }
    }

    render() {
        if(!this.props.loggedIn){
            return <div/>;
        }
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <canvas ref="canvas" width={512} height={512}/>
                    </div>
                </div>

            </div>
        )
    }
}