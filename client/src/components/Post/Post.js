import React, {Component} from "react";
import "./Post.css"

export default class Post extends Component {
    constructor(props) {
        super(props);
        this.state = {
            index: 0,
            user: "",
            date: "",
            lend: "",
            photo: "",
            isLoaded: false,
        }
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER +"get-photo/" + this.props.photo, {
            method: "GET",
            headers: {
                'content-type': 'image/jpeg'
            }
        }).then( res => res.blob().then( result =>
        {
            this.setState({
                isLoaded: true,
                photo: URL.createObjectURL(result),
                index: this.props.index,
                user: this.props.user,
                date: this.props.date,
                lend: this.props.lend,
            })
        }))
    }

    render() {
        let {isLoaded, index, user, date, lend, photo} = this.state;

        if (!isLoaded) {
            return <div>Loading... </div>
        } else {
            return (
                <div className={"post-div"}>
                    <table key={index} className={"post-table"}>
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
                                    <button className={"post_btn"}>👍🏻</button>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            );
        }
    }
}