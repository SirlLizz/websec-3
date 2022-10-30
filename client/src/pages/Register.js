import React, { useState } from 'react'
import axios from 'axios';
import validator from 'validator';
import domain from "../config/default.json"

export default function Register () {
    const DOMAIN_SERVER = domain.server;
    const DOMAIN_SITE = domain.site;

    const [register, setRegister] = useState(() => {
        return {
            username: "",
            email: "",
            password: "",
            password2: "",
        }
    })

    const changeInputRegister = event => {
        event.persist()
        setRegister(prev => {
            return {
                ...prev,
                [event.target.name]: event.target.value,
            }
        })
    }


    const submitChackin = async event => {
        event.preventDefault();
        if (!validator.isEmail(register.email)) {
            alert("You did not enter email")
        } else if (register.password !== register.password2) {
            alert("Repeated password incorrectly")
        } else if (!validator.isStrongPassword(register.password, {minSymbols: 0})) {
            alert("Password must consist of one lowercase, uppercase letter and number, at least 8 characters")
        } else {
            const response = await fetch("http://localhost:8080/register/", {
                method: "post",
                body: JSON.stringify({
                    name: register.username,
                    email: register.email,
                    password: register.password
                }),
                headers: {
                    'content-type': 'application/json'
                }
            }).catch(()=>{
                alert("An error occurred on the server")
            })
            const data = await response.json().catch(()=>{
                console.log("This user is already exist")
            })
            if(data!=null){
                console.log(data)
            }

        }
    }

    return (

        <div className="form">
            <h2>Register user:</h2>
            <form onSubmit={submitChackin}>
                <p>Логин: <input
                    type="username"
                    id="username"
                    name="username"
                    value={register.username}
                    onChange={changeInputRegister}
                /></p>
                <p>Email: <input
                    type="email"
                    id="email"
                    name="email"
                    value={register.email}
                    onChange={changeInputRegister}
                    formNoValidate
                /></p>
                <p>Пароль: <input
                    type="password"
                    id="password"
                    name="password"
                    value={register.password}
                    onChange={changeInputRegister}
                /></p>
                <p>Повторите пароль: <input
                    type="password"
                    id="password2"
                    name="password2"
                    value={register.password2}
                    onChange={changeInputRegister}
                /></p>
                <input type="submit"/>
            </form>
        </div>
    )
}