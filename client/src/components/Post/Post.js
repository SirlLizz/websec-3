import React, {Component} from "react";
import "./Post.css"

export default class Post extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: "",
            user: "",
            date: "",
            lend: "",
            photo: "",
            countLike: 1,
            isLoaded: false,
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
                        data => {
                            console.log(data)
                            this.setState({
                                isLoaded: true,
                                photo: URL.createObjectURL(blob_t),
                                countLike: data,
                                id: this.props.id,
                                user: this.props.user,
                                date: this.props.date,
                                lend: this.props.lend,
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
        let {isLoaded, id, user, date, lend, photo, countLike} = this.state;

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
                            console.log(data)
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
                <div className={"post-div"}>
                    <table key={id} className={"post-table"}>
                        <thead >
                            <tr>
                                <td className={"post-thead"}>
                                    <h2 className={"post-user-name"}>
                                        {user}
                                    </h2>
                                    <h>{
                                        date.slice(8,10) + "." + date.slice(5,7)+"." + date.slice(0,4)+
                                        "  " + date.slice(11,13) + ":" + date.slice(14,16)
                                    }</h>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <img className={"post-img"} src = {photo} alt={"post_photo"}></img>
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
                                    <button className={"post_btn"}>Комментарии</button>
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