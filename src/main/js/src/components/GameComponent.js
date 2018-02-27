import React from "react";

export default class GameComponent extends React.Component {


    constructor(props){
        super(props);
        // this.drawButton = this.drawButton.bind(this);
    }

    drawButton(){
        let cls;
        let text;
        if(Math.random() >= 0.5){
            cls = "btn btn-success";
            text = "it's your turn!";
        } else {
            cls = "btn btn-warning";
            text = "Opponent still needs to make their move.";
        }
        return (
            <button className={cls}>{text}</button>
        );
    }

    render(){
        return (
            <div>
                {this.drawButton()}
            </div>
        );
    }

}

