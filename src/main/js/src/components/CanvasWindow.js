import React from "react";
import axios from "axios";

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

function toBool(str) {
    if (str === "true") {
        return true;
    } else if (str === "false") {
        return false;
    }
    return undefined;
}

function getMousePos(canvas, evt) {
    const rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

let numClicks = 0;
let lastId = 0;
export default class CanvasWindow extends React.Component {
    state = {
        moveFrom: undefined,
        moveTo: undefined,
        gameState: {},
        selectedTile: undefined,
        shouldDraw: false
    };

    tick = () => {
        if (this.shouldPoll()) {
            this.poll();
            this.updateCanvas();
        }
    };

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    componentDidMount() {
        this.updateCanvas();
        this.tick();
        this.interval = setInterval(this.tick, 2000);
    }

    componentDidUpdate() {
        // want to check if it's the id that's been updated.
        // if it is, it means that the user has switched to a different active game and we want to poll and get the game state.
        if (lastId !== this.props.currentGameId) {
            lastId = this.props.currentGameId;
            this.poll();
        }
        this.updateCanvas();
    }

    shouldPoll = () => {
        return this.props.currentGameId !== undefined;
    };

    mapToChess = (x, y) => {
        const asciiCode = 65;
        const mappedInt = this.props.size - y;
        return String.fromCharCode(asciiCode + x) + "" + mappedInt;
    };

    drawCheck = (x, y) => {
        // if a king is in check, draw a circle around them.
        const check = this.state.gameState.check;

        const blackKingLocation = check["BLACK"]["location"];
        const whiteKingLocation = check["WHITE"]["location"];
        const ctx = this.getCtx();
        ctx.strokeStyle = "red";
        ctx.lineWidth = 3;
        if (this.mapToChess(x, y) === blackKingLocation) {
            const blackInCheck = toBool(check["BLACK"]["inCheck"]);
            if (blackInCheck) {
                ctx.beginPath();
                ctx.arc(
                    this.getSquareSize() / 2 + x * this.getSquareSize(),
                    this.getSquareSize() / 2 + y * this.getSquareSize(),
                    this.getSquareSize() / 2,
                    0,
                    2 * Math.PI
                );
                ctx.stroke();
            }
        } else if (this.mapToChess(x, y) === whiteKingLocation) {
            const whiteInCheck = toBool(check["WHITE"]["inCheck"]);
            if (whiteInCheck) {
                ctx.beginPath();
                ctx.arc(
                    this.getSquareSize() / 2 + x * this.getSquareSize(),
                    this.getSquareSize() / 2 + y * this.getSquareSize(),
                    this.getSquareSize() / 2,
                    0,
                    2 * Math.PI
                );
                ctx.stroke();
            }
        }
    };

    poll = () => {
        if (!this.props.playerId) {
            return;
        }

        axios.get(
            "/chess/v1/gamestate?gameId=" +
            this.props.currentGameId +
            "&playerId=" +
            this.props.playerId
        ).then(response => {
            this.setState(() => ({
                gameState: response.data,
                shouldDraw: true
            }));

            this.props.setGameStatus(response.data.gameStatus);
            this.props.setYourTurn(
                response.data.currentTurn === response.data.yourColour
            );
            this.props.setPlayerColour(response.data.yourColour);
        }).catch(err => {
            console.log(err);
        });
    };

    getCtx = () => {
        const canvas = this.refs.canvas;
        return canvas.getContext("2d");
    };

    drawSquare = (colour, x, y) => {
        const image = new Image();
        const currentChessPosition = this.mapToChess(x, y); // ex. 0,0 -> A8
        // the name of the image from the response matches the name of the image files.

        const gameOver = this.state.gameState.gameStatus === "FINISHED";
        if (/*!gameOver && */ this.state.shouldDraw) {
            // only draw the board when the game is ongoing.
            image.src =
                "images/" +
                this.state.gameState.positions[currentChessPosition] +
                ".png";
            image.onload = () => {
                const ctx = this.getCtx();
                ctx.fillStyle = colour;

                // draw a rectangle
                ctx.fillRect(
                    this.getSquareSize() * x,
                    this.getSquareSize() * y,
                    this.getSquareSize(),
                    this.getSquareSize()
                );

                // if the tile being drawn is the selected tile, draw it yellow instead.
                if (this.mapToChess(x, y) === this.state.selectedTile) {
                    ctx.fillStyle = "rgba(55,160,48,0.6)";
                    ctx.fillRect(
                        this.getSquareSize() * x,
                        this.getSquareSize() * y,
                        this.getSquareSize(),
                        this.getSquareSize()
                    );
                }

                // draw the opponenents moves.
                const lastMove = this.state.gameState.lastMove;
                if (lastMove) {
                    const mapped = this.mapToChess(x, y);
                    if (mapped == lastMove.from || mapped == lastMove.to) {
                        // display your own last move in blue, display the enemeies last move in red.
                        const myColour = "rgba(42,150,204, 0.6)"; // blue
                        const theirColour = "rgba(183, 5, 29, 0.6)"; // red
                        ctx.fillStyle =
                            this.state.gameState.currentTurn !==
                            this.state.gameState.yourColour
                                ? myColour
                                : theirColour;
                        ctx.fillRect(
                            this.getSquareSize() * x,
                            this.getSquareSize() * y,
                            this.getSquareSize(),
                            this.getSquareSize()
                        );
                    }
                }

                // and then an image on top of it.
                ctx.drawImage(
                    image,
                    this.getSquareSize() * x,
                    this.getSquareSize() * y,
                    this.getSquareSize(),
                    this.getSquareSize()
                );

                this.drawCheck(x, y);
            };
        }
    };

    updateCanvas = () => {
        for (let x = 0; x < this.props.size; x++) {
            for (let y = 0; y < this.props.size; y++) {
                if (isWhiteSquare(x, y)) {
                    this.drawSquare("#ffec96", x, y);
                } else {
                    this.drawSquare("#403f49", x, y);
                }
            }
        }
    };

    isTileEmpty = chessNotation => {
        return this.state.gameState.positions[chessNotation] === undefined;
    };

    canvasClicked = e => {
        const pos = getMousePos(this.refs.canvas, e);
        console.log(pos);
        const boardPos = {
            x: Math.floor(pos.x / this.getSquareSize()),
            y: Math.floor(pos.y / this.getSquareSize())
        };
        console.log(boardPos);
        const chessPos = this.mapToChess(boardPos.x, boardPos.y);

        numClicks++;
        if (numClicks === 1) {
            // first click
            if (this.isTileEmpty(chessPos)) {
                numClicks--;
                return; // don't do anything if the tile is empty.
            }

            this.setState(() => ({
                selectedTile: chessPos,
                moveFrom: chessPos
            }));
        } else if (numClicks === 2) {
            // second click
            this.setState(() => ({
                moveTo: chessPos,
                selectedTile: undefined
            }));
            numClicks = 0;
            this.makeMove(this.state.moveFrom, chessPos);
        }
    };

    makeMove = (moveFrom, moveTo) => {
        axios.post("/chess/v1/makemove", {
            from: moveFrom,
            to: moveTo,
            playerId: this.props.playerId,
            gameId: this.props.currentGameId
        })
            .then(() => {
                this.poll();
                this.updateCanvas();
            })
            .catch(err => {
                console.log(err);
            });
    };

    getSquareSize = () => {
        const canvas = this.refs.canvas;
        if (canvas.height / this.props.size < this.props.squareSize) {
            return canvas.height / this.props.size;
        }
        return this.props.squareSize;
    };

    determineSize = () => {
        const width = window.innerWidth;
        if (width >= 1196) {
            // lg or xl
            return 8 * 88;
        } else if (width >= 994) {
            return 8 * 73;
        } else if (width >= 769) {
            // md
            return 8 * 53;
        } else {
            // mobile screens.
            return width - 2 * (width / 8);
        }
    };

    render() {
        const classes = !this.props.loggedIn
            ? "container fade-anim hide canvas-border"
            : "container fade-anim canvas-border";
        return (
            <div>
                <canvas
                    onClick={this.canvasClicked}
                    className={classes}
                    ref="canvas"
                    width={this.determineSize()}
                    height={this.determineSize()}
                />
            </div>
        );
    }
}