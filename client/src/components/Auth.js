import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Register from "../pages/Register";
import validator from "validator";
import axios from "axios";
import domain from "../config/default.json";
import {Link} from "react-router-dom";

export default function Auth(){
    const DOMAIN_SERVER = domain.server;
    const DOMAIN_SITE = domain.site;
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const registerUser = () => {setShow(false); Register()}
    const handleShow = () => {setShow(true); }
    const onShow = () => {}

    const [auth, setAuth] = useState(() => {
        return {
            username: "",
            password: ""
        }
    })

    const changeInputAuth = event => {
        event.persist()
        setAuth(prev => {
            return {
                ...prev,
                [event.target.name]: event.target.value,
            }
        })
    }

    const submitChackin = event => {
        event.preventDefault();
        if(!validator.isEmail(auth.email)) {
            alert("You did not enter email")
        } else if(auth.password == null) {
            alert("Repeated password incorrectly")
        }
        else {
            axios.post(DOMAIN_SERVER + "/auth/registration/", {
                username: auth.username,
                password: auth.password,
            }).then(res => {
                if (res.data === true) {
                    window.location.href = DOMAIN_SITE + "/auth"
                } else {
                    alert("There is already a user with this email")
                }
            }).catch(() => {
                alert("An error occurred on the server")
            })
        }
    }

    return (
        <>
            <button className="contactbtn" onClick={handleShow}>Войти</button>
            <Modal show={show} onShow={onShow} onHide={handleClose}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered className="modal">
                <Modal.Body >
                    <form onSubmit={submitChackin}>
                        <p>Логин: <input
                            type="username"
                            id="username"
                            name="username"
                            value={auth.username}
                            onChange={changeInputAuth}
                        /></p>
                        <p>Пароль: <input
                            type="password"
                            id="password"
                            name="password"
                            value={auth.password}
                            onChange={changeInputAuth}
                        /></p>
                        <li>
                            <Link to="signup" onClick={handleClose}>Регистрация</Link>
                        </li>
                        <input type={"submit"} content={"Войти"}/>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}