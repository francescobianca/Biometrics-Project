import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import Box from '@material-ui/core/Box';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Badge from '@material-ui/core/Badge';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Link from '@material-ui/core/Link';
import AssignmentTurnedInIcon from '@material-ui/icons/AssignmentTurnedIn';
import DateFnsUtils from '@date-io/date-fns';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import NotificationsIcon from '@material-ui/icons/Notifications';
import Fab from '@material-ui/core/Fab'
import AddAPhotoIcon from '@material-ui/icons/AddAPhoto'
import Tooltip from '@material-ui/core/Tooltip'
import GetAppIcon from '@material-ui/icons/GetApp';
import { Avatar } from '@material-ui/core'
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
  } from '@material-ui/pickers';
import { mainListItems, secondaryListItems } from './listItems';
import Chart from './Chart';
import Cookies from 'js-cookie'
import { createBrowserHistory } from "history";
import Title from './Title';
import BaseInstance from '../http-client/BaseInstance'

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
      Biometric Project
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}



const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
  },
  depositContext: {
    flex: 1,
  },
}));


export default function FullWidthGrid({history}) {

    if (history === undefined) {
        history = createBrowserHistory()
        history.push("/login")
    }

    if (Cookies.get("matricolaProfessor") === undefined)
        history.push("/login");
    
    var imagePath = Cookies.get("profilePictureProfessor"); 
    if (imagePath === "null") 
        imagePath = require('../resources/default.png');


    var selectedCourse = Cookies.get("selectedCourse");
    var selectedCourseJSON = JSON.parse(selectedCourse);

    var courseLectures = Cookies.get("courseLectures");
    var courseLecturesArr = [];
   
    var courseLecturesJSON = JSON.parse(courseLectures);
    Object.keys(courseLecturesJSON).forEach(function(key) {
        courseLecturesArr.push(courseLecturesJSON[key]);
    });
    

    const classes = useStyles();
    //const [open, setOpen] = React.useState(true);
    const handleDrawerOpen = () => {
    setOpen(true);
    };
    const handleDrawerClose = () => {
    setOpen(false);
    };
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const [open, setOpen] = React.useState(false);
    const [selectedDate, setSelectedDate] = React.useState(new Date('2020-02-01T21:00:00'))
    const [description, setSelectedDescription] = React.useState("")

    //console.log(selectedCourseJSON.code)
    //var codeCourse = selectedCourseJSON.code;
    
    const handleClickOpen = () => {
        setOpen(true)
      }
    
      const handleClose = () => {
        setOpen(false)
      }
    
      const handleDateChange = date => {
        setSelectedDate(date)
      }
    
      const handleDescriptionChange = (e) => {
          setSelectedDescription(e.target.value)
      }

      const downloadSheet = (lectureId) => {
        BaseInstance.get("getSheet", { params: { "lectureId": lectureId } }).then(res => {
            console.log(res.data)
            var filename = res.data;
            BaseInstance.get(filename)
            window.location = 'http://localhost:8080/'+filename;
        })
      }

    //console.log(courseLecturesJSON)
    //console.log(selectedCourseJSON)

    const createLecture = () =>  {

        var courseCode = selectedCourseJSON.code;
        console.log(courseCode);
        console.log(description);
        console.log(selectedDate);

        BaseInstance.post("createLecture", {course: courseCode, description: description, date: selectedDate}).then(res =>{
            console.log(res);
            Cookies.set("todayLesson", res.data);
        })
        handleClose()
        window.location.reload();
    };

    var todayLesson = Cookies.get("todayLesson");
    console.log(todayLesson);

    // Quando clicco termina faccio l'update su today lesson, la levo dai cookie e la metto sotto nelle lezioni finite aggiornando la pagina.
    const closeLesson = (lectureId) => {
        console.log(lectureId);
        BaseInstance.get("closeLecture", { params: { "lectureId": lectureId } }).then(res => {
            Cookies.remove("todayLesson");
            //Cookies.remove("selectedCourse");
            //Cookies.remove("courseLectures");
            history.push("/homeProfessor");         
        })
    }

    // Quando clicco termina faccio l'update su today lesson, la levo dai cookie e la metto sotto nelle lezioni finite aggiornando la pagina.

    if (todayLesson != null) 
        return (
        <div className={classes.root}>
        <CssBaseline />
        <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
            <Toolbar className={classes.toolbar}>
            <IconButton
                edge="start"
                color="inherit"
                aria-label="open drawer"
                onClick={handleDrawerOpen}
                className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
            >
                <MenuIcon />
            </IconButton>
            <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
                Course management
            </Typography>
            
            </Toolbar>
        </AppBar>
        <Drawer
            variant="permanent"
            classes={{
            paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
            }}
            open={open}
        >
            <div className={classes.toolbarIcon}>
            <IconButton onClick={handleDrawerClose}>
                <ChevronLeftIcon />
            </IconButton>
            </div>
            <Divider />
            <List>{mainListItems}</List>
            <Divider />
            <List>{secondaryListItems}</List>
        </Drawer>
        <main className={classes.content}>
            <div className={classes.appBarSpacer} />
            <Container maxWidth="lg" className={classes.container}>
            <Grid container spacing={3}>
                
                {/* Profile Picture */}
                <Grid item xs={12} md={4} lg={3}>
                <React.Fragment>
                    <div style={{"margin-bottom" : "-40px"}}>
                        <Avatar alt="profile image" src={imagePath} style={{width: "60%", height: "auto", "marginLeft": "20%"}} />
                        <Tooltip title="Update profile picture" >
                            <Fab component="label" style={{"position":"relative", "bottom":"40px", "marginLeft":"65%", "backgroundColor":"#4151a7"}}>
                                <AddAPhotoIcon style={{ color: "white" }}/>
                                <input type="file" style={{ display: "none" }} 
                                        accept=".jpg,.jpeg,.png,.bmp" />
                            </Fab>
                        </Tooltip>
                    </div>
                </React.Fragment>
                </Grid>

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>You have selected the course : {selectedCourseJSON.code} - {selectedCourseJSON.name}</Title>
                </Paper>            
                </Grid>

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <div style={{ display:"inline-block", float:"right", paddingLeft:"42%" }}>
                <Fab className={classes.lectureCreation} color="primary" onClick={handleClickOpen} variant="extended">
                    Create Lecture
                </Fab>
                <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">New Lecture</DialogTitle>
                    <DialogContent>
                    <form>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="description"
                            label="Description"
                            onChange={handleDescriptionChange}
                            required
                            fullWidth
                        />
                        <MuiPickersUtilsProvider utils={DateFnsUtils}>
                            <Grid container justify="space-around">
                                <KeyboardDatePicker
                                margin="normal"
                                id="date-picker-dialog"
                                label="Day of the lecture"
                                format="dd/MM/yyyy"
                                value={selectedDate}
                                onChange={handleDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                                required
                            />
                            </Grid>
                        </MuiPickersUtilsProvider>
                    </form>
                    </DialogContent>
                    <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={createLecture} color="primary">
                        Create
                    </Button>
                    </DialogActions>
                </Dialog>
                </div>
                </Paper>    
                </Grid>

                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>Current Lesson:</Title>
                    
                        <ListItem className={classes.courseItem} item xs={12}>
                            <ListItem button>
                                <ListItemText > {JSON.parse(todayLesson).date}</ListItemText>
                            </ListItem>
                            <ListItem button>
                                <ListItemText > {JSON.parse(todayLesson).description}</ListItemText>
                            </ListItem>
                            <Tooltip title="Close the attendance registration for this lesson" >
                                <IconButton onClick={() => { closeLesson(JSON.parse(todayLesson).lectureId)}}> <AssignmentTurnedInIcon /> </IconButton>
                            </Tooltip>  
                        </ListItem>
                                
                </Paper>            
                </Grid>

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>List of past lessons:</Title>
                
                    {courseLecturesArr.map((lessons) => (
                        <div key={lessons.lectureId}>
                        <ListItem className={classes.courseItem} item xs={12}>
                            <ListItem button>
                                <ListItemText >{lessons.date}</ListItemText>
                            </ListItem>
                            <ListItem button>
                                <ListItemText >{lessons.description}</ListItemText>
                            </ListItem>
                            <Tooltip title="Download the attendance sheet" >
                                <IconButton onClick={() => { downloadSheet(lessons.lectureId) }} > <GetAppIcon /> </IconButton>
                            </Tooltip>
                        </ListItem>
                        <Divider></Divider>
                    </div>
                ))}
                
                </Paper>            
                </Grid>

            </Grid>
            <Box pt={4}>
                <Copyright />
            </Box>
            </Container>
        </main>
        </div>
  );
  else 
    return (
    <div className={classes.root}>
        <CssBaseline />
        <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
            <Toolbar className={classes.toolbar}>
            <IconButton
                edge="start"
                color="inherit"
                aria-label="open drawer"
                onClick={handleDrawerOpen}
                className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
            >
                <MenuIcon />
            </IconButton>
            <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
                Course management
            </Typography>
            
            </Toolbar>
        </AppBar>
        <Drawer
            variant="permanent"
            classes={{
            paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
            }}
            open={open}
        >
            <div className={classes.toolbarIcon}>
            <IconButton onClick={handleDrawerClose}>
                <ChevronLeftIcon />
            </IconButton>
            </div>
            <Divider />
            <List>{mainListItems}</List>
            <Divider />
            <List>{secondaryListItems}</List>
        </Drawer>
        <main className={classes.content}>
            <div className={classes.appBarSpacer} />
            <Container maxWidth="lg" className={classes.container}>
            <Grid container spacing={3}>
                
                {/* Profile Picture */}
                <Grid item xs={12} md={4} lg={3}>
                <React.Fragment>
                    <div style={{"margin-bottom" : "-40px"}}>
                        <Avatar alt="profile image" src={imagePath} style={{width: "60%", height: "auto", "marginLeft": "20%"}} />
                        <Tooltip title="Update profile picture" >
                            <Fab component="label" style={{"position":"relative", "bottom":"40px", "marginLeft":"65%", "backgroundColor":"#4151a7"}}>
                                <AddAPhotoIcon style={{ color: "white" }}/>
                                <input type="file" style={{ display: "none" }} 
                                        accept=".jpg,.jpeg,.png,.bmp" />
                            </Fab>
                        </Tooltip>
                    </div>
                </React.Fragment>
                </Grid>

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>You have selected the course : {selectedCourseJSON.code} - {selectedCourseJSON.name}</Title>
                </Paper>            
                </Grid>

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <div style={{ display:"inline-block", float:"right", paddingLeft:"42%" }}>
                <Fab className={classes.lectureCreation} color="primary" onClick={handleClickOpen} variant="extended">
                    Create Lecture
                </Fab>
                <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">New Lecture</DialogTitle>
                    <DialogContent>
                    <form>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="description"
                            label="Description"
                            onChange={handleDescriptionChange}
                            required
                            fullWidth
                        />
                        <MuiPickersUtilsProvider utils={DateFnsUtils}>
                            <Grid container justify="space-around">
                                <KeyboardDatePicker
                                margin="normal"
                                id="date-picker-dialog"
                                label="Day of the lecture"
                                format="dd/MM/yyyy"
                                value={selectedDate}
                                onChange={handleDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                                required
                            />
                            </Grid>
                        </MuiPickersUtilsProvider>
                    </form>
                    </DialogContent>
                    <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={createLecture} color="primary">
                        Create
                    </Button>
                    </DialogActions>
                </Dialog>
                </div>
                </Paper>    
                </Grid>

            

                {/* Professor Course */}
                <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>List of past lessons:</Title>
                
                    {courseLecturesArr.map((lessons) => (
                        <div key={lessons.lectureId}>
                        <ListItem className={classes.courseItem} item xs={12}>
                            <ListItem button>
                                <ListItemText >{lessons.date}</ListItemText>
                            </ListItem>
                            <ListItem button>
                                <ListItemText >{lessons.description}</ListItemText>
                            </ListItem>
                            <Tooltip title="Download the attendance sheet" >
                                <IconButton onClick={() => { downloadSheet(lessons.lectureId) }}> <GetAppIcon /> </IconButton>
                            </Tooltip>
                        </ListItem>
                        <Divider></Divider>
                    </div>
                ))}
                
                </Paper>            
                </Grid>

            </Grid>
            <Box pt={4}>
                <Copyright />
            </Box>
            </Container>
        </main>
        </div>
    );
}