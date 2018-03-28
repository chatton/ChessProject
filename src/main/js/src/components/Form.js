import React from "react";
import axios from "axios";

export default class Form extends React.Component {
    state = {
        badRequest: false
    };

    makePostRequest = endpoint => {
        const nameField = this.refs.form.elements.name;
        const name = nameField.value;
        nameField.value = "";
        const passField = this.refs.form.elements.password;
        const password = passField.value;
        passField.value = "";
        if (!name || !password) {
            return;
        }

        axios.post("/chess/v1/" + endpoint, {
            userName: name,
            password: password
        }).then(response => {
            const data = response.data;
            if (data.status === "OK") {
                // means the user has logged in ok.
                this.setState(() => ({
                    userName: name,
                    badRequest: false
                }));

                this.props.setPlayerName(name);
                this.props.updateId(data.id);
                this.props.setLoggedIn(true);
            } else if (data.status === "BAD") {
                this.setState(() => ({ badRequest: true }));
            }
        })
            .catch(error => {
                console.log(error);
            });
    };

    login = e => {
        e.preventDefault();
        this.makePostRequest("login");
    };

    register = e => {
        e.preventDefault();
        this.makePostRequest("register");
    };

    displayLoggedInMessage = () => {
        if (this.state.badRequest) {
            return (
                <div className="alert alert-danger">
                    <strong>User already exists!</strong> Pick a different user name.
                </div>
            );
        }
        return <div />;
    };

    renderForm = () => {
        const classes = this.props.loggedIn
            ? "container fade-anim hide slider"
            : "container fade-anim";
        return (
            <div className={classes + " fadeAndMoveUp"}>
                <div className="row">
                    <div className="col-md-3 col-lg-3 col-12" />

                    <form ref="form" className="col-md-6 col-lg-6 col-12">
                        {this.displayLoggedInMessage()}
                        <div className="form-group">
                            <input
                                type="email"
                                className="form-control"
                                name="name"
                                id="name"
                                placeholder="Username"
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="password"
                                className="form-control"
                                name="password"
                                id="password"
                                placeholder="Password"
                            />
                        </div>
                        <button
                            className="col-lg-6 col-md-6 col-sm-6 col-6 btn btn-primary"
                            onClick={this.register}
                        >
                            Register
                        </button>
                        <button
                            className="col-lg-6 col-md-6 col-sm-6 col-6 btn btn-success"
                            onClick={this.login}
                        >
                            Login
                        </button>
                    </form>
                    <div className="col-md-3" />
                </div>
            </div>
        );
    };

    render() {
        return <div>{this.renderForm()}</div>;
    }
}