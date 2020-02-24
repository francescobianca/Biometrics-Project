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
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Link from '@material-ui/core/Link';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import SubscriptionsIcon from '@material-ui/icons/Subscriptions';
import NotificationsIcon from '@material-ui/icons/Notifications';
import { mainListItems, secondaryListItems } from './listItems';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Chart from './Chart';
import Title from './Title';
import Fab from '@material-ui/core/Fab'
import AddAPhotoIcon from '@material-ui/icons/AddAPhoto'
import Tooltip from '@material-ui/core/Tooltip'
import { Avatar } from '@material-ui/core'
import BaseInstance from '../http-client/BaseInstance'
import Cookies from 'js-cookie'
import { createBrowserHistory } from "history";

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

export default function Dashboard({history}) {

    if (history === undefined) {
        history = createBrowserHistory()
        history.push("/login")
    }

    if (Cookies.get("matricolaStudent") === undefined)
        history.push("/login");
    
    var imagePath = Cookies.get("profilePictureStudent"); 
    if (imagePath === "null") 
        imagePath = require('../resources/default.png');

    var followingCourses = Cookies.get("followingCourses");
    console.log(followingCourses)
    var followingCoursesJSON =  JSON.parse(followingCourses);
    var arrFollowingCourses = [];
    Object.keys(followingCoursesJSON).forEach(function(key) {
      arrFollowingCourses.push(followingCoursesJSON[key]);
    });

    var availableCourse = Cookies.get("availableCourse");
    //console.log(availableCourse)

    //console.log(availableCourse)
    var availableCourseJSON = JSON.parse(availableCourse)
    //console.log(availableCourseJSON)
    var arrAvailableCourses = [];

    Object.keys(availableCourseJSON).forEach(function(key) {
      arrAvailableCourses.push(availableCourseJSON[key]);
    });

    //console.log(arrAvailableCourses);

    var studentName = Cookies.get("firstNameStudent");
    var studentSurname = Cookies.get("lastNameStudent")
    var studentMatricola = Cookies.get("matricolaStudent");

    const subscribe = (courseId,courseItem) => {
      //console.log(courseId)
      BaseInstance.get("subscribeCourse", { params: { "courseId": courseId, "matricola" : studentMatricola} }).then(res => {
          console.log(res.data)
          Cookies.set("followingCourses",res.data);

          BaseInstance.get("getAvailableCourse", { params: { "matricola": studentMatricola } }).then(res => {
            console.log(res.data)
            Cookies.set("availableCourse", res.data);
            window.location.reload();
          })

          //window.location.reload();
      })

      /*

      window.location.reload();*/
      
    }

  const classes = useStyles();
  const [open, setOpen] = React.useState(true);
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="absolute" className={clsx(classes.appBar)}>
        <Toolbar className={classes.toolbar}>
          
          <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
            Home Page: {studentName} {studentSurname}
          </Typography>
          
        </Toolbar>
      </AppBar>
 
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

            {/* My course */}
            <Grid item xs={12}>
              <Paper className={classes.paper}>
              <Title>My course:</Title>
               
              {arrFollowingCourses.map((course) => (
                    <div key={course.code}>
                    <ListItem className={classes.courseItem} item xs={12}>
                        <ListItem button>
                            <ListItemText >{course.code}</ListItemText>
                        </ListItem>
                        <ListItem button>
                            <ListItemText >{course.name}</ListItemText>
                        </ListItem>
                    </ListItem>
                    <Divider></Divider>
                </div>
               ))}

              </Paper>
            </Grid>

            {/* Available Course */}
            <Grid item xs={12}>
                <Paper className={classes.paper}>
                <Title>Available lessons:</Title>
                
                  {arrAvailableCourses.map((courseAvailable) => (
                      <div key={courseAvailable.code}>
                      <ListItem className={classes.courseItem} item xs={12}>
                          <ListItem button>
                              <ListItemText >{courseAvailable.code}</ListItemText>
                          </ListItem>
                          <ListItem button>
                              <ListItemText >{courseAvailable.name}</ListItemText>
                          </ListItem>
                          <Tooltip title="Subscribe at this course" >
                            <IconButton onClick={() => { subscribe(courseAvailable.code, courseAvailable) }}> <SubscriptionsIcon /> </IconButton>
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