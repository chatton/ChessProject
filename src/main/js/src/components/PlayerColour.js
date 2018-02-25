import React from "react";

export default class PlayerColour extends React.Component {

    constructor(props){
        super(props);
    }

    render() {
        return (
            <div>
                {this.props.loggedIn && this.props.playerColour && <h1>You are the {this.props.playerColour} Player</h1>}
            </div>
        );
    }
}
