import React from "react";
import _ from "lodash"

export default class HeaderComponent extends React.Component {


    determineSubtitle = () => {
        const possibleOptions = [
            "In life as in chess, forethought wins",
            "Chess is not for timid souls",
            "In boxing you create a strategy to beat each new oppoenent, it's just like chess."
        ];

        return _.sample(possibleOptions);
    }

    render(){
        if(this.props.loggedIn){
            return <div/>
        } else {
            return (
                <div className="container chess-background">
                <div className="col-xs-12 padded-top"></div>
                <div className="row">
                    <div className="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                        <h1 className="page-header large-h1 white outline"><em>{this.props.title}</em></h1>
                        <p className="white medium-p">{this.determineSubtitle()}</p>
                    </div>
                </div>
                <div className="col-xs-12 padded-bot"></div>
            </div>
            );
        }
    } 
}

