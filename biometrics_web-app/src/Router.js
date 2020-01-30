import React from "react";
import { createBrowserHistory } from "history";
import { Router, Switch, Route, Redirect } from "react-router";

import Home from './components/Home'
import LoginHandler from './components/LoginHandler'

const history = createBrowserHistory()

export default class Routes extends React.Component {
    render() {
        return (
            <Router history={history}>
                <Switch>
                    <Route path="/login" component={LoginHandler} />
                    <Route path="/home">
                        <Home history={history} />
                    </Route>
                 
                    <Route exact path="/" render={() => (
                        <Redirect to="/login" />
                    )} />
                </Switch>
            </Router>
        );
    }
    


    componentWillUnmount = () => {
        clearInterval(this.timer)
        this.timer = null;
    }



}