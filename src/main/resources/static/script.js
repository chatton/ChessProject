// Gets a handle to the element with id Chess.
const canvas = document.getElementById("Chess");
// Set the canvas up for drawing in 2D.
const ctx = canvas.getContext("2d");

const GRID_SIZE = 63;
const BOARD_SIZE = 8;

let shouldDraw = false;
let board = {"positions": {}};// create empty object to start with so the draw method knows to draw an empty tile.


// Map an x/y co-ordinate to the chess location.
function mapToChess(x, y) {
    const AasciiCode = 65;
    const mappedInt = BOARD_SIZE - y;
    return String.fromCharCode(AasciiCode + x) + "" + mappedInt;
}


function getMousePos(canvas, evt) {
    const rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

canvas.addEventListener("click", function (e) {
    const pos = getMousePos(canvas, e);
    const boardPos = {
        x: Math.floor(pos.x / GRID_SIZE),
        y: Math.floor(pos.y / GRID_SIZE)
    };
    const chessNotation = mapToChess(boardPos.x, boardPos.y);
    alert(board.positions[chessNotation] + " at " + chessNotation);
});

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

function draw() {
    for (let x = 0; x < BOARD_SIZE; x++) {
        for (let y = 0; y < BOARD_SIZE; y++) {
            if (isWhiteSquare(x, y)) {
                drawSquare("white", x, y);
            }
            else {
                drawSquare("black", x, y)
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
        // draw a rectangle
        ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);
        // and then an image on top of it.
        ctx.drawImage(image, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);
    };
}

let gameId;

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
            console.log(data);

            // this polling function will query the server every 5
            // seconds, we can use this to continually update game state.
            function poll() {
                console.log("Polling...");

                /*
                get will be used to get a new board
                 */
                $.get("/chess/v1/gamestate?gameId=" + gameId, function (data) {
                    board = data;
                    shouldDraw = true;
                });
                setTimeout(poll, 5000)
            }

            setTimeout(poll, 5000);
        })

    });
}

function start() {
    if (shouldDraw) {
        draw();
    }
    window.requestAnimationFrame(start);
}

draw(); // draw the initial board before the first GET request finishes/
start();

