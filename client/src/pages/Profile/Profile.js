import React, {Component} from 'react';
import "./Profile.css"
import AddPost from "../../components/AddPost/AddPost";
import Post from "../../components/Post/Post";

export default class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userPost: [],
            isLoaded: false,
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER + "user-posts/", {
            method: "GET",
            headers: {
                'content-type': 'application/json'
            },
            credentials: "include"
        }).then(
            res => {
                res.json().catch(() => {
                    document.getElementById("error_field").textContent = "Error in login or password"
                }).then(
                    data => {
                        console.log(data)
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

         function signout(){
             fetch(process.env.REACT_APP_DOMAIN_SERVER + "signout/", {
                 method: "POST",//тут должен быть DELETE, но cors политика не позволила...
                 headers: {
                     'content-type': 'application/json'
                 },
                 credentials: "include"
             }).then(
                 res => {
                     res.json().then(
                         data => {
                             if (data != null) {
                                 document.cookie = data.name+"=; Max-Age=0"
                                 window.location.assign(process.env.REACT_APP_DOMAIN_SITE);
                             }
                         }
                     )
                 }).catch(() => {
                 alert("An error occurred on the server")
             })
         }

        if (!isLoaded) {
            return <div>Loading... </div>
        } else {
            return (
                <>
                    <div className={"profile_btn_div"}>
                        <button onClick={signout} className={"profile_btn"}>Выйти</button>
                        <AddPost/>
                    </div>
                    {userPost.map((item) =>(
                        <Post id = {item.id}
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