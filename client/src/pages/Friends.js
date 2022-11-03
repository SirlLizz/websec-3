import React, { Component } from "react";

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

        if (!isLoaded) {
            return <div>Loading... </div>
        } else {
            return (
                <div>
                    <p>This Friends Page</p>
                    {friendRows.map(item => (
                        <div>
                            <p key={item.name+"_p"}>{item.name}</p>
                            <button key={item.name+"_button"}>{item.follow.toString()}</button>
                        </div>
                    ))}
                </div>
            );
        }
    }
}