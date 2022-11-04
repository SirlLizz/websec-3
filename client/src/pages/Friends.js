import React, { Component } from "react";
import "./Friends.css";

export default class Friends extends Component {
    constructor(props) {
        super(props);
        this.state = {
            friendRows: [],
            isLoaded: false,
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER + "get-all-user/", {
            method: "GET",
            headers: {
                'content-type': 'application/json'
            },
            credentials: "include"
        }).then(
            res => {
                console.log(res)
                res.json().catch(() => {
                    document.getElementById("error_field").textContent = "Error in login or password"
                }).then(
                    data => {
                        console.log(data)
                        this.setState({
                            isLoaded: true,
                            friendRows: data,
                        })
                    }
                )
            }).catch(() => {
            alert("An error occurred on the server")
        })
    }


    render() {
        let {isLoaded, friendRows} = this.state;

        function followFunction(name) {
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "follow-to-user/" + name, {
                method: "POST",
                headers: {
                    'content-type': 'application/json'
                },
                credentials: "include"
            }).then(
                res => {
                    //window.location.reload()
                    let button = document.createElement("button")
                    button.className = "button friend_button_follow"
                    button.textContent = "Подписан"
                    button.onclick = function() { unfollowFunction(name)}
                    button.id = name+"_button"
                    button.key = name+"_button"
                    document.getElementById(name+"_button").replaceWith(button)
                }).catch(() => {
                alert("An error occurred on the server")
            })
        }

        function unfollowFunction(name) {
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "unfollow-to-user/" + name, {
                method: "POST",
                headers: {
                    'content-type': 'application/json'
                },
                credentials: "include"
            }).then(
                res => {
                    //window.location.reload()
                    let button = document.createElement("button")
                    button.className = "button friend_button_no_follow"
                    button.textContent = "Подписаться"
                    button.onclick = function() { followFunction(name)}
                    button.id = name+"_button"
                    button.key = name+"_button"
                    document.getElementById(name+"_button").replaceWith(button)
                }).catch(() => {
                alert("An error occurred on the server")
            })
        }

        if (!isLoaded) {
            return <div>Loading... </div>
        } else {
            return (
                <div>
                    <table style={{margin: "auto"}}>
                        <tbody>
                        {friendRows.map((item, index) => (
                            <tr>
                                <td>
                                    <p key={item.name + "_p"} className={"friend_field"}>{item.name}</p>
                                </td>
                                <td>
                                    {
                                        item.follow ? (
                                            <button key={item.name+"_button"} id={item.name+"_button"} onClick={function() { unfollowFunction(item.name)}} className={"button friend_button_follow"}>Подписан</button>
                                        ):(
                                            <button key={item.name+"_button"} id={item.name+"_button"} onClick={function() { followFunction(item.name)}} className={"button friend_button_no_follow"}>Подписаться</button>
                                        )
                                    }
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            );
        }
    }
}