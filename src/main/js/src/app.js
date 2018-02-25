class Form extends React.Component {

    constructor(props) {
        super(props);
        this.register = this.register.bind(this);
        this.logout = this.logout.bind(this);
        this.state = {
            loggedIn: false,
            badRequest: false
        }
    }

    register(e) {
        e.preventDefault();
        const nameField = this.refs.form.elements.name;
        const name = nameField.value;
        nameField.value = "";
        const passField = this.refs.form.elements.password;
        const password = passField.value;
        passField.value = "";
        if (!name || !password) {
            return;
        }


        axios.post("/chess/v1/register", {
            userName: name,
            password: password
        }).then(response => {
            const data = response.data;
            console.log(data);
            if (data.status === "OK") { // means the user has logged in.
                this.setState(() => {
                    return {
                        userName: name,
                        loggedIn: true,
                        badRequest: false
                    }
                });

                // updates the playerId in the App component.
                console.log(this.props);
                console.log(data.id);
                this.props.updateId(data.id);
                // save the user id
            } else if (data.status === "BAD") {
                this.setState(() => {
                    return {
                        loggedIn: false,
                        badRequest: true
                    }
                });
            }

        }).catch(error => {
            console.log(error);
        })

    }


    displayLoggedInMessage() {
        if (this.state.badRequest) {
            return <h2 className="bg-danger">User already exists! Pick a new user name.</h2>
        }
        if (this.state.loggedIn) {
            return <h2 className="bg-success">Logged in as {this.state.userName}</h2>
        }
    }

    logout() {
        this.setState(() => {
            return {
                loggedIn: false
            }
        });
    }


    renderForm() {
        if (!this.state.loggedIn) {
            return (
                <form ref="form">
                    <div className="form-group">
                        <label htmlFor="name">Email address</label>
                        <input type="email" className="form-control" name="name" id="name" placeholder="User Name" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="exampleInputPassword1">Password</label>
                        <input type="password" className="form-control" name="password" id="password" placeholder="Password" />
                    </div>
                    <button className="btn btn-normal" onClick={this.register}>Register</button>
                </form>
            );
        }
    }


    render() {
        return (
            <div>
                {this.displayLoggedInMessage()}
                {this.renderForm()}
                {this.state.loggedIn && <button className="btn btn-default" onClick={this.logout}>Logout</button>}
            </div>
        );
    }
}



class NewGameButton extends React.Component {

    constructor(props){
        super(props);
        this.requestNewGame = this.requestNewGame.bind(this);
    }

    requestNewGame(){
        const id = this.props.playerId;
        axios.get("/chess/v1/newgame?playerId=" + id)
            .then(response => console.log(response))

    }

    render(){
        return (
            <div>
                {this.props.playerId !== undefined  && <button className="btn-success" onClick={this.requestNewGame}>New Game</button>}
            </div>
        );
    }
}

class App extends React.Component {

    constructor(props){
        super(props);
        this.setPlayerId = this.setPlayerId.bind(this);
        this.state = {
            playerId : undefined
        }
    }

    setPlayerId(id){
        this.setState(() => ({playerId:id}));
    }

    render(){
        return (
            <div>
                <Form updateId={this.setPlayerId}/>
                <NewGameButton playerId={this.state.playerId}/>
                {/*<CanvasWindow size={8}/>*/}
            </div>
        );
    }
}





function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}



class CanvasWindow extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            gameState : {},
            shouldDraw : false
        }
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
                ctx.arc(GRID_SIZE / 2 + x * GRID_SIZE, GRID_SIZE / 2 + y * GRID_SIZE, GRID_SIZE / 2, 0, 2 * Math.PI);
                ctx.stroke();
            }
        } else if (this.mapToChess(x, y) === whiteKingLocation) {
            const whiteInCheck = check["WHITE"]["inCheck"] === "true";
            if (whiteInCheck) {
                ctx.beginPath();
                ctx.arc(GRID_SIZE / 2 + x * GRID_SIZE, GRID_SIZE / 2 + y * GRID_SIZE, GRID_SIZE / 2, 0, 2 * Math.PI);
                ctx.stroke();
            }
        }
    }

    poll(){
        axios.get("/chess/v1/gamestate?gameId=" + this.props.gameId + "&playerId=" + this.props.playerId)
            .then(function(response){
                this.setState(() => ({
                    gameState : response.data,
                    shouldDraw : true,
                    selectedTile : undefined,
                    shouldDrawGrid : true
                }));
            });
        // setTimeout(poll, 5000)
    }

    componentDidMount() {
        this.updateCanvas();
        this.poll();
    }

    componentDidUpdate() {
        this.updateCanvas();
    }

    getCtx(){
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
                ctx.fillRect(GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);

                // and then an image on top of it.
                ctx.drawImage(image, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE);

                drawCheck(x, y);

                if (gameOver) {
                    this.setState(() => ({shouldDrawGrid:false}));
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

    render(){
        return (
            <canvas ref="canvas" width={300} height={300}/>
        )
    }
}

CanvasWindow.defaultProps = {
    gameId : 1,
    playerId : 1
};


ReactDOM.render(<App/>, document.getElementById("app"));
