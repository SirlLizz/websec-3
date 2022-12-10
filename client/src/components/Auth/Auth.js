import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import {Link} from "react-router-dom";
import "./Auth.css"

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

    const submitAuth = event => {
        event.preventDefault();
        if (auth.username === "") {
            document.getElementById("error_field").textContent = "You did not enter email"
        } else if (auth.password == null) {
            document.getElementById("error_field").textContent = "Repeated password incorrectly"
        } else {
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "auth/", {
                method: "POST",
                body: JSON.stringify({
                    user:{
                        name: auth.username,
                        password: auth.password,
                    }
                }),
                headers: {
                    'content-type': 'application/json'
                }
            }).then(
                res => {
                    console.log(res)
                    res.json().catch(() => {
                    document.getElementById("error_field").textContent = "Error in login or password"
                }).then(
                    data => {
                        if (data != null) {
                            document.cookie = data.name+"="+data.value
                            handleClose();
                            window.location.assign(process.env.REACT_APP_DOMAIN_SITE + "feed");
                        }
                    }
                )
                }).catch(() => {
                alert("An error occurred on the server")
            })
        }
    }

    return (
        <>
            <Link className="nav-link" onClick={handleShow}>Войти</Link>
            <Modal show={show} onShow={onShow} onHide={handleClose}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered className="modal">
                <Modal.Body >
                    <form onSubmit={submitAuth}>
                        <table style={{margin: "auto"}}>
                            <tbody>
                            <tr>
                                <td>
                                    <p style={{margin: "auto"}}>Логин или Email:</p>
                                </td>
                                <td>
                                    <input
                                        type="username"
                                        id="username"
                                        name="username"
                                        value={auth.username}
                                        onChange={changeInputAuth}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td >
                                    <p style={{margin: "auto"}} >Пароль:</p>
                                </td>
                                <td>
                                    <input
                                        type="password"
                                        id="password"
                                        name="password"
                                        value={auth.password}
                                        onChange={changeInputAuth}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    <p id="error_field"></p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <Link className={"auth-btn"} to="signup" onClick={handleClose}>Регистрация</Link>
                                </td>
                                <td>
                                    <button className={"auth-btn"}>Войти</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}