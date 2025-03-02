import React, { useState } from 'react'
import validator from 'validator';
import "./Register.css"

export default function Register () {

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
            document.getElementById("error_field").textContent = "You did not enter email"
        } else if (register.password !== register.password2) {
            document.getElementById("error_field").textContent = "Repeated password incorrectly"
        } else if (!validator.isStrongPassword(register.password, {minSymbols: 0})) {
            document.getElementById("error_field").textContent = "Password must consist of one lowercase, uppercase letter and number, at least 8 characters"
        } else {
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "register/", {
                method: "POST",
                body: JSON.stringify({
                    user:{
                        name: register.username,
                        email: register.email,
                        password: register.password,
                    },
                }),
                headers: {
                    'content-type': 'application/json'
                }
            }).then(
                res => {
                    res.json().catch(() => {
                        document.getElementById("error_field").textContent = "This user is already exist"
                    }).then(
                        data => {
                            if (data != null) {
                                document.cookie = data.name+"="+data.value
                                window.location.assign(process.env.REACT_APP_DOMAIN_SITE + "profile");
                            }
                        }
                    )
                }).catch(() => {
                alert("An error occurred on the server")
            })
        }
    }


    return (

        <div className="form">
            <h2>Регистрация:</h2>
            <form onSubmit={submitChackin}>
                <table style={{margin: "auto"}}>
                    <tbody>
                        <tr>
                            <td>
                                <p style={{margin: "auto"}}>Логин:</p>
                            </td>
                            <td>
                                <input
                                    type="username"
                                    id="username"
                                    name="username"
                                    value={register.username}
                                    onChange={changeInputRegister}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p style={{margin: "auto"}}>Email:</p>
                            </td>
                            <td>
                                <input
                                    type="email"
                                    id="email"
                                    name="email"
                                    value={register.email}
                                    onChange={changeInputRegister}
                                    formNoValidate
                                />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p style={{margin: "auto"}}>Пароль:</p>
                            </td>
                            <td>
                                <input
                                    type="password"
                                    id="password"
                                    name="password"
                                    value={register.password}
                                    onChange={changeInputRegister}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p style={{margin: "auto"}}>Повторите пароль:</p>
                            </td>
                            <td>
                                <input
                                    type="password"
                                    id="password2"
                                    name="password2"
                                    value={register.password2}
                                    onChange={changeInputRegister}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <p id="error_field"></p>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <button className={"register-btn"}>Зарегистрироваться</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    )
}