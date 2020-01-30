import React, {useState} from "react";
import Cookies from 'js-cookie'
import { createBrowserHistory } from "history";
import BaseInstance from '../http-client/BaseInstance'

import Button from '@material-ui/core/Button';

export default function LoginHandler ({history}) {

    if(history === undefined) {
        history = createBrowserHistory()
        history.push("/login")
    }

   

    return (
        <div>
            <h1>Login Page</h1>

            <Button variant="contained" color="primary">
                Hello World
            </Button>

        </div>
    );

};