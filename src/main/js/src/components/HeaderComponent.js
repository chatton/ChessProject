import React from "react";

export default class HeaderComponent extends React.Component {
    render(){
        return (
            <div className="container chess-background">
            <div className="col-xs-12 padded-top"></div>
                <div className="row">
                    <div className="col-md-3 col-lg-3 col-sm-3">
                        <img className="img-responsive" src="https://qph.fs.quoracdn.net/main-qimg-24dbdd54250c244db75b507afc70ec09" 
                        style={{height : "60%", width : "60%"}}/>
                    </div>
                    <div className="col-md-9 col-lg-9 col-sm-9 col-xs-9">
                        <h1 className="page-header large-h1 white outline"><em>{this.props.title}</em></h1>
                        <p className="white medium-p">A witty subtitle about chess</p>
                    </div>
                </div>
                <div className="col-xs-12 padded-bot"></div>
            </div>
        );
    }
}