import React, {Component} from 'react';
import Modal from "react-bootstrap/Modal";
import "./Comment.css"

export default class Comment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            post_id: "",
            commentList: [],
            countComment:0,
            textComment:"",
            isLoaded: false,
            show: false
        }
    }


    componentDidMount() {
        fetch(process.env.REACT_APP_DOMAIN_SERVER + "get-comment/" + this.props.post_id, {
            method: "GET",
            headers: {
                'content-type': 'application/json'
            },
            credentials: "include"
        }).then(
            res => {
                res.json().then(
                    data => {
                        fetch(process.env.REACT_APP_DOMAIN_SERVER +"get-count-comment/" + this.props.post_id,{
                            method: "GET",
                            headers: {
                                'content-type': 'application/json'
                            },
                            credentials: "include"
                        }).then(
                            res_comment => {
                                res_comment.json().then(
                                    fetch_count_comment => {
                                        console.log(data)
                                        this.setState({
                                            isLoaded: true,
                                            commentList: data,
                                            post_id: this.props.post_id,
                                            countComment: fetch_count_comment,
                                            textComment: this.state.textComment
                                        })
                                    }
                                )
                            }).catch(() => {
                                alert("An error occurred on the server")
                            }
                        )
                    }
                )
            }).catch(() => {
            alert("An error occurred on the server")
        })
    }

    render() {
        let {isLoaded, show, countComment, commentList, textComment, post_id} = this.state;
        const handleClose = () => {
            this.setState({ show: false })
        }
        const handleShow = () => {
            this.setState({ show: true })
        }

        const changeComment = event => {
            event.persist()
            this.setState(prev => {
                return {
                    ...prev,
                    [event.target.name]: event.target.value,
                }
            })
        }

        function addComment(){
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "add-comment/", {
                method: "POST",
                body: JSON.stringify({
                    post:{
                        id:post_id
                    },
                    text: textComment
                }),
                headers: {
                    'content-type': 'application/json'
                },
                credentials: "include"
            }).then(
                res => {
                    res.json().then(
                        data => {
                            /*const newContent = document.createTextNode("Hi there and greetings!")
                            const lastComment = document.getElementById("comment_div_4")
                            const parentDiv = document.getElementById("modal");
                            lastComment.parentNode.insertBefore(lastComment, newContent);*/
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
                    <button className={"comment_btn"} onClick={handleShow}>Комментарии {countComment}</button>
                    <Modal show={show} onHide={handleClose}
                        size="lg"
                           aria-labelledby="contained-modal-title-vcenter"
                           centered className="modal"
                           >
                        <Modal.Body >
                            <div id={"modal"}>
                                {commentList.map((item, index) =>(
                                    <div key={index} id={"comment_div_"+index} className={"comment_div"}>
                                        <h6>{item.user.name}: {item.text}</h6>
                                        <p>{item.date.slice(8,10) + "." + item.date.slice(5,7)+"." + item.date.slice(0,4)+
                                            "  " + item.date.slice(11,13) + ":" + item.date.slice(14,16)}</p>
                                    </div>
                                ))}
                                <textarea className={"comment_textarea"}
                                          id="textComment"
                                          name="textComment"
                                          value={textComment}
                                          onChange={changeComment}/>
                                <button className={"comment_btn"} onClick={addComment}>Отправить</button>
                            </div>
                        </Modal.Body>
                    </Modal></>
            );
        }
    }
}