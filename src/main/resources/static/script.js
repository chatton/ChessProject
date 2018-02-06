// Gets a handle to the element with id Chess.
const canvas = document.getElementById("Chess");
// Set the canvas up for drawing in 2D.
const ctx = canvas.getContext("2d");

let shouldraw = false;
let board = {};
$.get("/chess/v1/gamestate", function(data) {
    board = data;
    shouldraw = true;
});

// Map an x/y co-ordinate to the chess location.
function mapToChess(x, y) {
    const a_ascii_code = 65;
    const mapped_int = BOARD_SIZE - y;
    return String.fromCharCode(a_ascii_code + x) + "" + mapped_int;
}

const GRID_SIZE = 63;
const BOARD_SIZE = 8;

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
            if (shouldraw) {
                if (isWhiteSquare(x, y)) {
                    drawSquare("white", x, y);
                }
                else {
                    drawSquare("black", x, y)
                }
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

function drawButton(){
    // 1. Create the button
    var button = document.getElementById("myBtn");
    button.innerHTML = "Do Something";

    // 2. Append somewhere
    var body = document.getElementsByTagName("body")[0];
    body.appendChild(button);

    // 3. Add event handler
    button.addEventListener ("click", function() {
        alert("did something");
    });
}

function start() {
    draw();
    //drawButton();
    window.requestAnimationFrame(start);
}

start();

