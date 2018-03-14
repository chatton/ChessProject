import React from "react";

export default class LogoutButton extends React.Component {


    logout = () => {
        this.props.setLoggedIn(false);
    }

    render() {
        const classes = !this.props.loggedIn ? "container fade-anim hide" : "container fade-anim"
        return (
            <div className={classes}>
                <button className="btn btn-warning btn-block" onClick={this.logout}>Logout</button>
            </div>
        );
    }
}