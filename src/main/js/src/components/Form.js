import React from "react";
import axios from 'axios';

export default class Form extends React.Component {

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

                // updates the playerId in the ChessApp component.
                console.log(this.props);
                console.log(data.id);
                this.props.updateId(data.id);
                this.props.setLoggedIn(true);
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
        this.props.setLoggedIn(false);
    }


    renderForm() {
        if (!this.state.loggedIn) {
            return (
                <form ref="form">
                    <div className="form-group">
                        <label htmlFor="name">Username</label>
                        <input type="email" className="form-control" name="name" id="name" placeholder="Username"/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="exampleInputPassword1">Password</label>
                        <input type="password" className="form-control" name="password" id="password"
                               placeholder="Password"/>
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
