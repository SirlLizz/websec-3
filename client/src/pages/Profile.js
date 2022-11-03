import React from 'react';
export default function Profile() {

    const signout = event => {
        event.preventDefault();
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
        <div>
            <p>This Your Profile</p>
            <button onClick={signout}>Выйти</button>
        </div>


    );
}