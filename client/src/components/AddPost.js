import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import "./Auth.css"
import "./AddPost.css"

export default function AddPost(){

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => {setShow(true); }
    const onShow = () => {}

    const [post, setPost] = useState(() => {
        return {
            lend: ""
        }
    })

    const changePost = event => {
        event.persist()
        setPost(prev => {
            return {
                ...prev,
                [event.target.name]: event.target.value,
            }
        })
    }

    const add = event => {
        event.preventDefault();
        let photo = document.getElementById("file").files[0]
        if (photo === undefined) {
            document.getElementById("error_field").textContent = "You did not enter photo"
        } else if (post.lend === "") {
            document.getElementById("error_field").textContent = "You did not enter lend"
        } else {
            const formData  = new FormData();
            formData.append("lend", post.lend)
            formData.append("photo", photo)
            fetch(process.env.REACT_APP_DOMAIN_SERVER + "new-post/", {
                method: "POST",
                body: formData,
                credentials: "include"
            }).then(
                res => {
                    console.log(res)
                    res.json().then(
                        data => {
/*                            if (data != null) {
                                console.log(data)
                                handleClose();
                                window.location.assign(process.env.REACT_APP_DOMAIN_SITE + "profile");
                            }*/
                        }
                    )
                }).catch(() => {
                alert("An error occurred on the server")
            })
        }
    }


    return (
        <>
            <button className={"profile_btn"} onClick={handleShow}>Новый пост</button>
            <Modal show={show} onShow={onShow} onHide={handleClose}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered className="modal">
                <Modal.Body >
                    <form onSubmit={add}>
                        <table style={{margin: "auto"}}>
                            <tbody>
                                <tr>
                                    <td>
                                        <p style={{margin: "auto"}}>Подпись:</p>
                                    </td>
                                    <td>
                                        <textarea style={{width:"100%"}}
                                            id="lend"
                                            name="lend"
                                            value={post.lend}
                                            onChange={changePost}
                                        />
                                    </td>
                                </tr>
                                <tr>
                                    <td >
                                        <p style={{margin: "auto"}} >Фото:</p>
                                    </td>
                                    <td>
                                        <input
                                            type="file"
                                            name="file"
                                            id="file"
                                            accept="image/jpeg,image/png"
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
                                        <button className={"add-btn"}>Добавить</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}