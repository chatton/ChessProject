"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Form = function (_React$Component) {
    _inherits(Form, _React$Component);

    function Form(props) {
        _classCallCheck(this, Form);

        var _this = _possibleConstructorReturn(this, (Form.__proto__ || Object.getPrototypeOf(Form)).call(this, props));

        _this.register = _this.register.bind(_this);
        _this.logout = _this.logout.bind(_this);
        _this.state = {
            loggedIn: false,
            badRequest: false
        };
        return _this;
    }

    _createClass(Form, [{
        key: "register",
        value: function register(e) {
            var _this2 = this;

            e.preventDefault();
            var nameField = this.refs.form.elements.name;
            var name = nameField.value;
            nameField.value = "";
            var passField = this.refs.form.elements.password;
            var password = passField.value;
            passField.value = "";
            if (!name || !password) {
                return;
            }

            axios.post("/chess/v1/register", {
                userName: name,
                password: password
            }).then(function (response) {
                var data = response.data;
                console.log(data);
                if (data.status === "OK") {
                    // means the user has logged in.
                    _this2.setState(function () {
                        return {
                            userName: name,
                            loggedIn: true,
                            badRequest: false
                        };
                    });

                    // updates the playerId in the App component.
                    console.log(_this2.props);
                    console.log(data.id);
                    _this2.props.updateId(data.id);
                    _this2.props.setLoggedIn(true);
                    // save the user id
                } else if (data.status === "BAD") {
                    _this2.setState(function () {
                        return {
                            loggedIn: false,
                            badRequest: true
                        };
                    });
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    }, {
        key: "displayLoggedInMessage",
        value: function displayLoggedInMessage() {
            if (this.state.badRequest) {
                return React.createElement(
                    "h2",
                    { className: "bg-danger" },
                    "User already exists! Pick a new user name."
                );
            }
            if (this.state.loggedIn) {
                return React.createElement(
                    "h2",
                    { className: "bg-success" },
                    "Logged in as ",
                    this.state.userName
                );
            }
        }
    }, {
        key: "logout",
        value: function logout() {
            this.setState(function () {
                return {
                    loggedIn: false
                };
            });
            this.props.setLoggedIn(false);
        }
    }, {
        key: "renderForm",
        value: function renderForm() {
            if (!this.state.loggedIn) {
                return React.createElement(
                    "form",
                    { ref: "form" },
                    React.createElement(
                        "div",
                        { className: "form-group" },
                        React.createElement(
                            "label",
                            { htmlFor: "name" },
                            "Username"
                        ),
                        React.createElement("input", { type: "email", className: "form-control", name: "name", id: "name", placeholder: "Username" })
                    ),
                    React.createElement(
                        "div",
                        { className: "form-group" },
                        React.createElement(
                            "label",
                            { htmlFor: "exampleInputPassword1" },
                            "Password"
                        ),
                        React.createElement("input", { type: "password", className: "form-control", name: "password", id: "password",
                            placeholder: "Password" })
                    ),
                    React.createElement(
                        "button",
                        { className: "btn btn-normal", onClick: this.register },
                        "Register"
                    )
                );
            }
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                null,
                this.displayLoggedInMessage(),
                this.renderForm(),
                this.state.loggedIn && React.createElement(
                    "button",
                    { className: "btn btn-default", onClick: this.logout },
                    "Logout"
                )
            );
        }
    }]);

    return Form;
}(React.Component);

var NewGameButton = function (_React$Component2) {
    _inherits(NewGameButton, _React$Component2);

    function NewGameButton(props) {
        _classCallCheck(this, NewGameButton);

        var _this3 = _possibleConstructorReturn(this, (NewGameButton.__proto__ || Object.getPrototypeOf(NewGameButton)).call(this, props));

        _this3.requestNewGame = _this3.requestNewGame.bind(_this3);
        return _this3;
    }

    _createClass(NewGameButton, [{
        key: "requestNewGame",
        value: function requestNewGame() {
            var _this4 = this;

            var id = this.props.playerId;
            axios.get("/chess/v1/newgame?playerId=" + id).then(function (response) {
                var data = response.data;
                console.log(data);
                _this4.props.setPlayerColour(data.colour);
                console.log("setting game id as: " + data.gameId);
                _this4.props.setCurrentGameId(data.gameId);
            });
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                null,
                this.props.playerId !== undefined && React.createElement(
                    "button",
                    { className: "btn-success", onClick: this.requestNewGame },
                    "New Game"
                )
            );
        }
    }]);

    return NewGameButton;
}(React.Component);

var PlayerColour = function (_React$Component3) {
    _inherits(PlayerColour, _React$Component3);

    function PlayerColour(props) {
        _classCallCheck(this, PlayerColour);

        return _possibleConstructorReturn(this, (PlayerColour.__proto__ || Object.getPrototypeOf(PlayerColour)).call(this, props));
    }

    _createClass(PlayerColour, [{
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                null,
                this.props.loggedIn && this.props.playerColour && React.createElement(
                    "h1",
                    null,
                    "You are the ",
                    this.props.playerColour,
                    " Player"
                )
            );
        }
    }]);

    return PlayerColour;
}(React.Component);

var App = function (_React$Component4) {
    _inherits(App, _React$Component4);

    function App(props) {
        _classCallCheck(this, App);

        var _this6 = _possibleConstructorReturn(this, (App.__proto__ || Object.getPrototypeOf(App)).call(this, props));

        _this6.setPlayerId = _this6.setPlayerId.bind(_this6);
        _this6.setPlayerColour = _this6.setPlayerColour.bind(_this6);
        _this6.setLoggedIn = _this6.setLoggedIn.bind(_this6);
        _this6.setCurrentGameId = _this6.setCurrentGameId.bind(_this6);

        _this6.state = {
            playerId: undefined,
            playerColour: undefined,
            loggedIn: false,
            currentGameId: undefined
        };
        return _this6;
    }

    /*
     updates the player id, this is called by the FormComponent
     in order to pass it down as props to the NewGameButtonComponent
     */


    _createClass(App, [{
        key: "setPlayerId",
        value: function setPlayerId(id) {
            this.setState(function () {
                return { playerId: id };
            });
        }
    }, {
        key: "setPlayerColour",
        value: function setPlayerColour(colour) {
            this.setState(function () {
                return { playerColour: colour };
            });
        }
    }, {
        key: "setLoggedIn",
        value: function setLoggedIn(value) {
            this.setState(function () {
                return { loggedIn: value };
            });
        }
    }, {
        key: "setCurrentGameId",
        value: function setCurrentGameId(id) {
            this.setState(function () {
                return { currentGameId: id };
            });
        }
    }, {
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                null,
                React.createElement(Form, { updateId: this.setPlayerId, setLoggedIn: this.setLoggedIn }),
                React.createElement(NewGameButton, {
                    playerId: this.state.playerId,
                    setPlayerColour: this.setPlayerColour,
                    setCurrentGameId: this.setCurrentGameId
                }),
                React.createElement(PlayerColour, { playerColour: this.state.playerColour, loggedIn: this.state.loggedIn }),
                React.createElement(CanvasWindow, {
                    size: 8,
                    squareSize: 64,
                    currentGameId: this.state.currentGameId,
                    loggedIn: this.state.loggedIn,
                    playerId: this.state.playerId
                })
            );
        }
    }]);

    return App;
}(React.Component);

function isWhiteSquare(x, y) {
    return x % 2 === y % 2;
}

var CanvasWindow = function (_React$Component5) {
    _inherits(CanvasWindow, _React$Component5);

    function CanvasWindow(props) {
        _classCallCheck(this, CanvasWindow);

        var _this7 = _possibleConstructorReturn(this, (CanvasWindow.__proto__ || Object.getPrototypeOf(CanvasWindow)).call(this, props));

        _this7.poll = _this7.poll.bind(_this7);
        _this7.state = {
            gameState: {},
            shouldDraw: false
        };
        return _this7;
    }

    _createClass(CanvasWindow, [{
        key: "shouldPoll",
        value: function shouldPoll() {
            return this.props.currentGameId !== undefined;
        }
    }, {
        key: "mapToChess",
        value: function mapToChess(x, y) {
            var asciiCode = 65;
            var mappedInt = this.props.size - y;
            return String.fromCharCode(asciiCode + x) + "" + mappedInt;
        }
    }, {
        key: "drawCheck",
        value: function drawCheck(x, y) {
            // if a king is in check, draw a circle around them.
            var check = this.state.gameState.check;
            var blackKingLocation = check["BLACK"]["location"];
            var whiteKingLocation = check["WHITE"]["location"];
            var ctx = this.getCtx();
            ctx.strokeStyle = "red";
            ctx.lineWidth = 3;
            if (this.mapToChess(x, y) === blackKingLocation) {
                var blackInCheck = check["BLACK"]["inCheck"] === "true";
                if (blackInCheck) {
                    ctx.beginPath();
                    ctx.arc(this.props.squareSize / 2 + x * this.props.squareSize, this.props.squareSize / 2 + y * this.props.squareSize, this.props.squareSize / 2, 0, 2 * Math.PI);
                    ctx.stroke();
                }
            } else if (this.mapToChess(x, y) === whiteKingLocation) {
                var whiteInCheck = check["WHITE"]["inCheck"] === "true";
                if (whiteInCheck) {
                    ctx.beginPath();
                    ctx.arc(this.props.squareSize / 2 + x * this.props.squareSize, this.props.squareSize / 2 + y * this.props.squareSize, this.props.squareSize / 2, 0, 2 * Math.PI);
                    ctx.stroke();
                }
            }
        }
    }, {
        key: "poll",
        value: function poll() {
            var _this8 = this;

            console.log("polling...");
            axios.get("/chess/v1/gamestate?gameId=" + this.props.currentGameId + "&playerId=" + this.props.playerId).then(function (response) {
                console.log("From server: ");
                console.log(response.data);
                _this8.setState(function () {
                    return {
                        gameState: response.data,
                        shouldDraw: true,
                        selectedTile: undefined,
                        shouldDrawGrid: true
                    };
                });
            });

            setTimeout(this.poll, 2000);
        }
    }, {
        key: "componentDidMount",
        value: function componentDidMount() {
            this.updateCanvas();
        }
    }, {
        key: "componentDidUpdate",
        value: function componentDidUpdate() {
            if (this.shouldPoll()) {
                this.poll();
            }
            this.updateCanvas();
        }
    }, {
        key: "getCtx",
        value: function getCtx() {
            var canvas = this.refs.canvas;
            return canvas.getContext("2d");
        }
    }, {
        key: "drawSquare",
        value: function drawSquare(colour, x, y) {
            var _this9 = this;

            var image = new Image();
            var currentChessPosition = this.mapToChess(x, y); // ex. 0,0 -> A8
            // the name of the image from the response matches the name of the image files.

            var gameOver = this.state.gameState.gameStatus === "FINISHED";
            if (!gameOver && this.state.shouldDrawGrid) {
                // only draw the board when the game is ongoing.
                image.src = "images/" + this.state.gameState.positions[currentChessPosition] + ".png";
                image.onload = function () {
                    var ctx = _this9.getCtx();
                    ctx.fillStyle = colour;
                    // if the tile being drawn is the selected tile, draw it yellow instead.
                    if (_this9.mapToChess(x, y) === _this9.state.selectedTile) {
                        ctx.fillStyle = "yellow";
                    }

                    // draw a rectangle
                    ctx.fillRect(_this9.props.squareSize * x, _this9.props.squareSize * y, _this9.props.squareSize, _this9.props.squareSize);

                    // and then an image on top of it.
                    ctx.drawImage(image, _this9.props.squareSize * x, _this9.props.squareSize * y, _this9.props.squareSize, _this9.props.squareSize);

                    _this9.drawCheck(x, y);

                    if (gameOver) {
                        _this9.setState(function () {
                            return { shouldDrawGrid: false };
                        });
                    }
                };
            }
        }
    }, {
        key: "updateCanvas",
        value: function updateCanvas() {
            console.log("Updating canvas");
            for (var x = 0; x < this.props.size; x++) {
                for (var y = 0; y < this.props.size; y++) {
                    console.log("drawing square");
                    if (isWhiteSquare(x, y)) {
                        this.drawSquare("#ffec96", x, y);
                    } else {
                        this.drawSquare("#403f49", x, y);
                    }
                }
            }
        }
    }, {
        key: "render",
        value: function render() {
            if (!this.props.loggedIn) {
                return React.createElement("div", null);
            }
            return React.createElement(
                "div",
                { className: "container" },
                React.createElement(
                    "div",
                    { className: "row" },
                    React.createElement(
                        "div",
                        { className: "col" },
                        React.createElement("canvas", { ref: "canvas", width: 512, height: 512 })
                    )
                )
            );
        }
    }]);

    return CanvasWindow;
}(React.Component);

ReactDOM.render(React.createElement(App, null), document.getElementById("app"));
