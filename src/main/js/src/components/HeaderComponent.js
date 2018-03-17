import React from "react";
import _ from "lodash";

const allOptions = [
    "In life as in chess, forethought wins.",
    "Chess is not for timid souls.",
    "In boxing you create a strategy to beat each new oppoenent, it's just like chess.",
    "Even a poor plan is better than no plan.",
    "Methodical thinking is of more use in chess then inspiration.",
    "Chess is the art of analysis.",
    "Every Master was once a beginner.",
    "A good player is always lucky.",
    "The pawns are the soul of chess.",
    "The blunders are all there on the board, waiting to be made."
];

export default class HeaderComponent extends React.Component {

    state = {
        selected: _.sample(allOptions)
    }

    componentDidMount() {
        setInterval(() => this.setState({
            selected: _.sample(allOptions)
        }), 5000)
    }

    render() {
        if (this.props.loggedIn) {
            return <div />;
        } else {
            return (
                <div className="container">
                    <div className="col-12 padded-top" />
                    <div className="row">
                        <div className="col-md-12 col-lg-12 col-sm-12 col-12">
                            <h1 className="text-left page-header display-1 white outline">
                                <strong>{this.props.title}</strong>
                            </h1>
                            <h2 className="text-left white medium-p">
                                {this.state.selected}
                            </h2>
                        </div>
                    </div>
                    <div className="col-12 padded-bot" />
                </div>
            );
        }
    }
}