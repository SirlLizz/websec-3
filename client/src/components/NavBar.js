import React from 'react';
import Auth from "./Auth";
import {Link} from "react-router-dom";
import "./NavBar.css"

export default function NavBar() {

    return (
        <div className= "spacer">
            <section id="nav-bar">
                <nav className="navbar navbar-expand-lg navbar-dark">
                    <Link className="navbar-brand" to = "/">NELZYAGRAM</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation" id="navbar-toggler">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                     <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ml-auto" id = "navbar-after-login">
                            <span className="nav-item">
                                <Link className="nav-link" to="/feed">Новости</Link>
                            </span>
                            <span className="nav-item">
                                <Link className="nav-link" to="/profile">Профиль</Link>
                            </span>
                        </ul>
                         <ul className="navbar-nav ml-auto" id = "navbar-before-login">
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