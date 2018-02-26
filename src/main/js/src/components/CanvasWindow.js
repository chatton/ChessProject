import React from "react";
import axios from 'axios';

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

function getMousePos(canvas, evt) {
    const rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

let numClicks = 0;
export default class CanvasWindow extends React.Component {

    constructor(props) {
        super(props);
        this.canvasClicked = this.canvasClicked.bind(this);
        this.makeMove = this.makeMove.bind(this);
        this.poll = this.poll.bind(this);
        this.drawCheck = this.drawCheck.bind(this);
        this.tick = this.tick.bind(this);
        this.isTileEmpty = this.isTileEmpty.bind(this);

        this.state = {
            moveFrom : undefined,
            moveTo : undefined,
            gameState: {},
            selectedTile : undefined,
            shouldDraw: false
        }
    }

    tick() {
        if(this.shouldPoll()){
            this.poll();
            this.updateCanvas();
        }
        // setInterval(this.tick, 5000);
    }
      
    componentWillUnmount() {
        clearInterval(this.interval);
    }
    
    componentDidMount() {
        this.updateCanvas();
        this.tick();
        this.interval = setInterval(this.tick, 5000);
    }

    componentDidUpdate() {
        this.updateCanvas();
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
        axios.get("/chess/v1/gamestate?gameId=" + this.props.currentGameId + "&playerId=" + this.props.playerId)
            .then(response => {
                this.setState(() => ({
                    gameState: response.data,
                    shouldDraw: true,
                    shouldDrawGrid: true
                }));
                console.log(response.data);
                this.props.setGameStatus(response.data.gameStatus);
                this.props.setYourTurn(response.data.currentTurn === response.data.yourColour);
            });

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
        // console.log("Drawing canvas.");
        for (let x = 0; x < this.props.size; x++) {
            for (let y = 0; y < this.props.size; y++) {
                if (isWhiteSquare(x, y)) {
                    this.drawSquare("#ffec96", x, y);
                }
                else {
                    this.drawSquare("#403f49", x, y)
                }
            }
        }
    }

    isTileEmpty(chessNotation){
        return this.state.gameState.positions[chessNotation] === undefined;
    }


    canvasClicked(e){
        const pos = getMousePos(this.refs.canvas, e);
        const boardPos = {
            x: Math.floor(pos.x / this.props.squareSize),
            y: Math.floor(pos.y / this.props.squareSize)
        };
        const chessPos = this.mapToChess(boardPos.x, boardPos.y);

        
        numClicks++;
        if(numClicks === 1) { // first click
            if(this.isTileEmpty(chessPos)){
                numClicks--;
                return; // don't do anything if the tile is empty.
            }

            this.setState(() => ({
                selectedTile : chessPos,
                moveFrom : chessPos
            }));

        } else if (numClicks === 2){ // second click
            this.setState(() => ({
                moveTo : chessPos,
                selectedTile : undefined
            }));
            numClicks = 0;
            this.makeMove(this.state.moveFrom, chessPos);
        }
    }

    makeMove(moveFrom, moveTo){
        console.log("Making move. From: " + moveFrom + " to " + moveTo);
        axios.post("/chess/v1/makemove", {
            from : moveFrom,
            to : moveTo,
            playerId :this.props.playerId,
            gameId : this.props.currentGameId
        }).then(()=>{
            this.poll();
            this.updateCanvas();
        }).catch((err)=>{
            console.log(err);
        });
        
    }

    render() {
        if(!this.props.loggedIn){
            return <div/>;
        }
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <canvas onClick={this.canvasClicked} ref="canvas" width={512} height={512}/>
                    </div>
                </div>

            </div>
        )
    }
}