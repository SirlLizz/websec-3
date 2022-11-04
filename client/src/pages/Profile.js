import React, {Component} from 'react';
import "./Profile.css"
import AddPost from "../components/AddPost";

export default class Profile extends Component {


    render() {

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

        return (
            <div className={"profile_btn_div"}>
                <button onClick={signout} className={"profile_btn"}>Выйти</button>
                <AddPost/>
            </div>
        );
    }


}