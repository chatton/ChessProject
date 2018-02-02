//<script type="text/javascript">
// Gets a handle to the element with id canvasOne.
canvas = document.getElementById("Chess");
// Set the canvas up for drawing in 2D.
ctx = canvas.getContext("2d");

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
    console.log("clicked!");
    const pos = getMousePos(canvas, e);

    alert(Math.floor(pos.x / GRID_SIZE) + " " + Math.floor(pos.y / GRID_SIZE));
});

function draw() {
    for (let x = 0; x < BOARD_SIZE; x++) {
        for (let y = 0; y < BOARD_SIZE; y++) {
            if (x % 2 === y % 2) {
                ctx.fillStyle = "black";
                ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);
                // Draw image here
            }
            else {
                ctx.fillStyle = "white";
                ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);
                // Draw image here
            }
        }
    }
}
function start(){
    draw();
    window.requestAnimationFrame(start);
}

start();
