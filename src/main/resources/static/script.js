//<script type="text/javascript">
// Gets a handle to the element with id canvasOne.
canvas = document.getElementById("Chess");
// Set the canvas up for drawing in 2D.
ctx = canvas.getContext("2d");

function getMousePos(canvas, evt)
{
    var rect = canvas.getBoundingClientRect();
    return{
        x:evt.clientX - rect.left,
        y:evt.clientY - rect.top
    };
}


function handleClick(e)
{

}
canvas.addEventListener("click", function (e) {
    console.log("clicked!");
    var pos = getMousePos(canvas, e);
    var posx = pos.x;
    var posy = pos.y;

    alert(posx + " " + posy);
});

function draw()
{
    for(var x=0; x < 8; x++)
    {
        for(var y=0; y < 8; y++)
        {
            if (x%2 === y%2)
            {
                ctx.fillStyle = "black";
                ctx.fillRect(63*x, 63*y, 63, 63);
            }
            else
            {
                ctx.fillStyle = "white";
                ctx.fillRect(63*x, 63*y, 63, 63);
            }
        }
    }
}
draw();