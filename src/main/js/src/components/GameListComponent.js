import React from "react";
import GameComponent from "./GameComponent";

export default class GameListComponent extends React.Component {

    constructor(props){
        super(props);
        this.drawButton = this.drawButton.bind(this);
        this.state = {
            list : [Math.random(), Math.random(), Math.random(),Math.random(),Math.random()]
        }
    }

    drawButton(num){
        let cls;
        let text;
        if(num >= 0.5){
            cls = "btn btn-success btn-block";
            text = "it's your turn!";
        } else {
            cls = "btn btn-warning btn-block";
            text = "Opponent still needs to make their move.";
        }
        return (
            <button className={cls}>{text}</button>
        );
    }

    render(){
        if(!this.props.loggedIn){
            return <div/>
        }
        return (
            <div>
                <h2 className="page-header">Ongoing Games</h2>
                <ul className="list-group">
                    {
                        this.state.list.map(num => {
                            return (<li className="list-group-item">{this.drawButton(num)}</li>);
                        })
                    }
                </ul>
            </div>
        );
    }
}

