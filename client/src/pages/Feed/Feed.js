import React, {Component} from 'react';
import Post from "../../components/Post/Post";

export default class Feed extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userPost: [],
            isLoaded: false,
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER + "follow-posts/", {
            method: "GET",
            headers: {
                'content-type': 'application/json'
            },
            credentials: "include"
        }).then(
            res => {
                res.json().then(
                    data => {
                        this.setState({
                            isLoaded: true,
                            userPost: data,
                        })
                    }
                )
            }).catch(() => {
            alert("An error occurred on the server")
        })
    }

    render() {
        let {isLoaded, userPost} = this.state;

        if (!isLoaded) {
            return <div>Loading... </div>
        } else {
            return (
                <>
                    {userPost.map((item) =>(
                        <Post key = {item.id+"_post"}
                              id = {item.id}
                              user = {item.user.name}
                              photo = {item.photo}
                              lend = {item.lend}
                              date = {item.date}/>
                    ))}
                </>

            );
        }
    }
}