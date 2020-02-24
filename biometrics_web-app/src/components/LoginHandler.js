import React, {useState} from "react";
import Cookies from 'js-cookie'
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { createBrowserHistory } from "history";
import BaseInstance from '../http-client/BaseInstance'
import {useStyles} from './LoginStyle'

function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit">
                Biometric Project
        </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

export default function LoginHandler ({history}) {

    if(history === undefined) {
        history = createBrowserHistory()
        history.push("/login")
    }

    // Da sistemare questa parte
    /*if(Cookies.get("matricola") !== undefined)
        history.push("/home");*/

        
    Cookies.remove("currentCourse")
    const [matricola, setMatricola] = useState("");
    const [password, setPassword] = useState("");
    const [login, setLogin] = useState(true);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const classes = useStyles();

    const setInitialSessionProfessor = async (data) => {
        Cookies.set("matricolaProfessor", data.matricola);
        Cookies.set("firstNameProfessor", data.firstName);
        Cookies.set("lastNameProfessor", data.lastName);
        Cookies.set("teachedCourses", data.teachedCourses);
        Cookies.set("profilePictureProfessor", data.profilePicture);
        //var subscribedCourses = data.followingCourses;
        //subscribedCourses.sort((a, b) => (a.subject > b.subject) ? 1 : -1)
        //Cookies.set("subscribedCourses", subscribedCourses);
        //var res = await  BaseInstance.post("/getAllCoursesAvailable", {email: data.email});
        //var allCourses = res.data
        //allCourses.sort((a, b) => (a.subject > b.subject) ? 1 : -1)
        //Cookies.set("allCourses", allCourses);
    }

    const setInitialSessionStudent = async (data) => {
        Cookies.set("matricolaStudent", data.matricola);
        Cookies.set("firstNameStudent", data.firstName);
        Cookies.set("lastNameStudent", data.lastName);
        Cookies.set("followingCourses", data.followingCourses);
        Cookies.set("profilePictureStudent", data.profilePicture);

        BaseInstance.get("getAvailableCourse", { params: { "matricola": data.matricola } }).then(res => {
            console.log(res.data)
            Cookies.set("availableCourse", res.data);
            history.push("/homeStudent");
        })
        //var subscribedCourses = data.followingCourses;
        //subscribedCourses.sort((a, b) => (a.subject > b.subject) ? 1 : -1)
        //Cookies.set("subscribedCourses", subscribedCourses);
        //var res = await  BaseInstance.post("/getAllCoursesAvailable", {email: data.email});
        //var allCourses = res.data
        //allCourses.sort((a, b) => (a.subject > b.subject) ? 1 : -1)
        //Cookies.set("allCourses", allCourses);
    }

    const handleSubmit = async e => {
        var res;
        
        res = await BaseInstance.post("login", { matricola: matricola, password: password });

        if(res.data.matricola !== undefined){
            if (res.data.type === false) {
                // Professor
                await setInitialSessionProfessor(res.data);
                history.push("/homeProfessor");
            }
            else if (res.data.type === true) {
                // student
                await setInitialSessionStudent(res.data);
                
            }
        } else {
            history.push("/login");
        }
        
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <img className={classes.logo} src={require('../resources/logo.png')}/>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined" margin="normal" required fullWidth id="matricola" label="Matricola"
                        name="matricola" onChange={e => setMatricola(e.target.value)} autoComplete="matricola" autoFocus />
                    <TextField
                        variant="outlined" margin="normal" required fullWidth name="password"
                        label="Password" type="password" id="password" onChange={e => setPassword(e.target.value)} autoComplete="current-password"  />
                    <Button
                            fullWidth variant="contained" color="primary" className={classes.buttonStyle} onClick={() => handleSubmit()}>
                            Login</Button>
                    <Grid container>
                        <Grid item>
                            <Link href="#" variant="body2" >
                                {"Gli studenti Sapienza devono obbligatoriamente accedere, senza creare alcun account, utilizzando i dati di InfoStud"}
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>
            <Box mt={8}>
                <Copyright />
            </Box>
        </Container>
    );

};