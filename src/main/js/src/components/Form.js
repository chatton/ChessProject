import React from "react";
import axios from 'axios';

export default class Form extends React.Component {

    state = {
        badRequest: false
    }

    setName = name => {
        this.setState(() => ({name}));
    }

    setBadRequest = badRequest => {
        this.setState(() => ({badRequest}));
    }

    register = e => {
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
            if (data.status === "OK") { // means the user has logged in.
                this.setState(() => ({             
                        userName: name,
                        badRequest: false,
                }));
                this.props.updateId(data.id);
                this.props.setLoggedIn(true);
            } else if (data.status === "BAD") {
                this.setState(() => ({badRequest: true}));
            }

        }).catch(error => {
            console.log(error);
        })

    }

    displayLoggedInMessage = () => {
        if (this.state.badRequest) {
            return (
                <div className="alert alert-danger">
                    <strong>User already exists!</strong> Pick a different user name.
                </div>
            );

        }
        if (this.props.loggedIn) {
            return(
                <div className="alert alert-success">
                    <strong>Success!</strong> You are Logged in as <strong>{this.state.userName}</strong>.
                </div>
            );
        }
    }

    logout = () => {
        this.props.setLoggedIn(false);
    }


    renderForm = () => {
        if (!this.props.loggedIn) {
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
                {this.props.loggedIn && <button className="btn btn-default" onClick={this.logout}>Logout</button>}
            </div>
        );
    }
}
