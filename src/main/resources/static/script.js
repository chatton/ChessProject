// Gets a handle to the element with id Chess.
const canvas = document.getElementById("Chess");
// Set the canvas up for drawing in 2D.
const ctx = canvas.getContext("2d");

const GRID_SIZE = 63;
const BOARD_SIZE = 8;

let shouldDraw = false;
let board = {"positions": {}, "check": {"WHITE": "", "BLACK": ""}};// create empty object to start with so the draw method knows to draw an empty tile.


// Map an x/y co-ordinate to the chess location.
function mapToChess(x, y) {
    const asciiCode = 65;
    const mappedInt = BOARD_SIZE - y;
    return String.fromCharCode(asciiCode + x) + "" + mappedInt;
}


function getMousePos(canvas, evt) {
    const rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

let numClicks = 0;
let move = {"from": "", "to": "", "playerId": ""};

function sendMove(data) {
    $.ajax({
        type: "POST",
        contentType: "application/json", // this is required by spring boot. Otherwise get a 415 error.
        data: JSON.stringify(data), // send object as string to server
        dataType: "json",
        url: window.location + "chess/v1/makemove",
        /*
         when the move is made, call poll to update the state of the board so the change
         appears right away instead of needing to wait up to 5 seconds to see the change take effect.
         */
        complete: poll
    });
}

function tileIsEmpty(chessNotation) {
    return board.positions[chessNotation] === undefined;
}

let selectedTile;
canvas.addEventListener("click", function (e) {
    const pos = getMousePos(canvas, e);
    const boardPos = {
        x: Math.floor(pos.x / GRID_SIZE),
        y: Math.floor(pos.y / GRID_SIZE)
    };
    const chessNotation = mapToChess(boardPos.x, boardPos.y);

    numClicks++;
    if (numClicks === 1) {
        if (tileIsEmpty(chessNotation)) {
            numClicks--;
            return;
        }

        selectedTile = chessNotation;
        move.from = chessNotation;
    } else if (numClicks === 2) {
        move.to = chessNotation;
        numClicks = 0;
        selectedTile = undefined;
        sendMove(move);
    }
});

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

function draw() {
    for (let x = 0; x < BOARD_SIZE; x++) {
        for (let y = 0; y < BOARD_SIZE; y++) {
            if (isWhiteSquare(x, y)) {
                drawSquare("#fff796", x, y);
            }
            else {
                drawSquare("#474931", x, y)
            }
        }
    }
}

// fills up the square of the chess board with either black or white.
function drawSquare(colour, x, y) {
    const image = new Image();
    const currentChessPosition = mapToChess(x, y); // ex. 0,0 -> A8
    // the name of the image from the response matches the name of the image files.
    image.src = "images/" + board["positions"][currentChessPosition] + ".png";
    image.onload = () => {
        ctx.fillStyle = colour;
        // if the tile being drawn is the selected tile, draw it yellow instead.
        if (mapToChess(x, y) === selectedTile) {
            ctx.fillStyle = "yellow";
        }

        // draw a rectangle
        ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);

        // if a king is in check, draw a circle around them.
        const check = board.check;

        if (mapToChess(x, y) === check.WHITE.location || mapToChess(x, y) === check.BLACK.location) {

            if (check.WHITE.inCheck || check.BLACK.inCheck) {
                ctx.strokeStyle = "red";
                ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);
            }
        }

        // and then an image on top of it.
        ctx.drawImage(image, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);

    };
}

let gameId;

// this polling function will query the server every 5
// seconds, we can use this to continually update game state.
function poll() {
    /*
     get will be used to get a new board
     */
    $.get("/chess/v1/gamestate?gameId=" + gameId + "&playerId=" + move.playerId, function (data) {
        board = data;
        move["gameId"] = gameId;
        shouldDraw = true;
        // TODO display in a label/image/another canvas
        $("textarea").val("You are playing as the " + board["yourColour"] + " player!");
    });

    setTimeout(poll, 5000)
}

function drawButton() {
    // 1. Create the button
    const button = document.getElementById("myBtn");
    button.innerHTML = "Do Something";

    // 2. Append somewhere
    const body = document.getElementsByTagName("body")[0];
    body.appendChild(button);

    // 3. Add event handler
    button.addEventListener("click", function () {
        $.get("/chess/v1/newgame", function (data) {
            gameId = data.gameId;
            move.playerId = data.playerId;
            poll();
        })
    });
}

function start() {
    if (shouldDraw) {
        draw();
    }
    window.requestAnimationFrame(start);
}


draw(); // draw the initial board before the first GET request finishes
start();