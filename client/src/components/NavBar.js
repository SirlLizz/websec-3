import React from 'react';
import {Button} from "react-bootstrap";
import Auth from "./Auth";

class NavBar extends React.Component {
    render() {
        return (
            <div className= "spacer">
                <section id="nav-bar">
                    <nav className="navbar navbar-expand-lg navbar-light">
                        <a className="navbar-brand">NELZYAGRAM</a>
                        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>

                        <div className="collapse navbar-collapse" id="navbarNav">
                            <ul className="navbar-nav ml-auto">
                                <span className="nav-item">
                                    <Auth/>
                                </span>
                            </ul>
                        </div>
                    </nav>
                </section>
            </div>
        );
    }
}
export default NavBar;