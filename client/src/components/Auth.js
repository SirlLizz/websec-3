import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Link} from "react-router-dom";

export default function Auth(){

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
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

    const submitAuth = async event => {
        event.preventDefault();
        if (auth.username === "") {
            document.getElementById("error_field").textContent = "You did not enter email"
        } else if (auth.password == null) {
            document.getElementById("error_field").textContent = "Repeated password incorrectly"
        } else {
            const response = await fetch(process.env.REACT_APP_DOMAIN_SERVER + "auth/", {
                method: "POST",//тут вообще как бы должен быть get, но fetch не хочет делать get c body
                body: JSON.stringify({
                    name: auth.username,
                    password: auth.password
                }),
                headers: {
                    'content-type': 'application/json'
                }
            }).catch(() => {
                alert("An error occurred on the server")
            })
            const data = await response.json().catch(() => {
                document.getElementById("error_field").textContent = "Error in login or password"
            })
            if (data != null) {
                document.getElementById("navbar-before-login").style.display = "none"
                document.getElementById("navbar-after-login").style.display = "flex"
                console.log(data)
                handleClose();
            }
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
                    <form onSubmit={submitAuth}>
                        <p>Логин или Email: <input
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
                        <p id="error_field"></p>
                        <li>
                            <Link to="signup" onClick={handleClose}>Регистрация</Link>
                        </li>
                        <button>Войти</button>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}