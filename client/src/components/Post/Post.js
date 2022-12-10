import React, {Component} from "react";
import "./Post.css"
import Comment from "../Comment/Comment";

export default class Post extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: "",
            user: "",
            date: "",
            lend: "",
            photo: "",
            countLike: 0,
            isLoaded: false
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER +"get-photo/" + this.props.photo, {
            method: "GET",
            headers: {
                'content-type': 'image/jpeg'
            }
        }).then( res => res.blob().then( blob_t =>
        {
            fetch(process.env.REACT_APP_DOMAIN_SERVER +"get-like/" + this.props.id,{
                method: "GET",
                headers: {
                    'content-type': 'application/json'
                },
                credentials: "include"
                }).then(
                res_like => {
                    res_like.json().then(
                        fetch_count_like => {
                            this.setState({
                                isLoaded: true,
                                photo: URL.createObjectURL(blob_t),
                                countLike: fetch_count_like,
                                id: this.props.id,
                                user: this.props.user,
                                date: this.props.date,
                                lend: this.props.lend
                            })
                        }
                    )
                }).catch(() => {
                alert("An error occurred on the server")
                }
            )
        }))
    }

    render() {
        let {isLoaded, id, user, date, lend, countLike} = this.state;

        function addLike(){
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "add-like/"+id+"/"+user, {
                method: "POST",
                headers: {
                    'content-type': 'application/json'
                },
                credentials: "include"
            }).then(
                res => {
                    res.json().then(
                        data => {
                            document.getElementById("like_btn_"+id).textContent = "👍🏻"+ data
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
                <div key={id+"_div"} className={"post-div"}>
                    <table key={id+"_table"} className={"post-table"}>
                        <thead >
                            <tr>
                                <td className={"post-thead"}>
                                    <h2 className={"post-user-name"}>
                                        {user}
                                    </h2>
                                    {
                                        date.slice(8,10) + "." + date.slice(5,7)+"." + date.slice(0,4)+
                                        "  " + date.slice(11,13) + ":" + date.slice(14,16)
                                    }
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <img className={"post-img"} src = {process.env.REACT_APP_DOMAIN_SERVER +"get-photo/" + this.props.photo} alt={"post_photo"}></img>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                {lend}
                            </td>
                        </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td className={"post-tfoot"}>
                                    <Comment key={id+"_comment"}
                                             post_id={id}
                                    />
                                    <button id={"like_btn_"+id} className={"post_btn"} onClick={addLike}>👍🏻{countLike}</button>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            );
        }
    }
}