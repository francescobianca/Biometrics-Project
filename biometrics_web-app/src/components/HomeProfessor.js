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
import CheckIcon from '@material-ui/icons/Check';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Link from '@material-ui/core/Link';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import NotificationsIcon from '@material-ui/icons/Notifications';
import Fab from '@material-ui/core/Fab'
import AddAPhotoIcon from '@material-ui/icons/AddAPhoto'
import Tooltip from '@material-ui/core/Tooltip'
import { Avatar } from '@material-ui/core'
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

export default function Dashboard({history}) {

    if (history === undefined) {
        history = createBrowserHistory()
        history.push("/login")
    }

    if (Cookies.get("matricolaProfessor") === undefined)
        history.push("/login");
    
    var imagePath = Cookies.get("profilePictureProfessor"); 
    if (imagePath === "null") 
        imagePath = require('../resources/default.png');

    
    var courses = Cookies.get("teachedCourses");
    var coursesJSON =  JSON.parse(courses);
    var arr = [];
    Object.keys(coursesJSON).forEach(function(key) {
      arr.push(coursesJSON[key]);
    });

    //console.log(arr);

    const classes = useStyles();
    const [open, setOpen] = React.useState(true);
    const handleDrawerOpen = () => {
    setOpen(true);
    };
    const handleDrawerClose = () => {
    setOpen(false);
    };
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const openCoursePage = (course, code) => {
        Cookies.set("selectedCourse", course);
        BaseInstance.get("getCourseLectures", { params: { "code": code } }).then(res => {
          Cookies.set("courseLectures",res.data)
        })
        history.push('/professorCoursePage');
    }

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
            Home Page Professore
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
              <Title>List of courses</Title>
                {arr.map((course) => (
                    <div key={course.code}>
                    <ListItem className={classes.courseItem} item xs={12}>
                        <ListItem button>
                            <ListItemText >{course.code}</ListItemText>
                        </ListItem>
                        <ListItem button>
                            <ListItemText >{course.name}</ListItemText>
                        </ListItem>
                        <IconButton onClick={() => { openCoursePage(course,course.code) }}> <CheckIcon /> </IconButton>
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