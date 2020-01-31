import { makeStyles } from '@material-ui/core/styles';
import { height } from '@material-ui/system';

export const useStyles = makeStyles(theme => ({
    paper: {
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    logo: {
      width:'150px',
      height: '150px'
    },
    buttonStyle: {
      color: 'white',
      margin: theme.spacing(3, 0, 2),
      background: 'linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(6,6,69,1) 34%, rgba(0,212,255,1) 100%);',
    },
    form: {
      width: '100%',
      marginTop: theme.spacing(3),
    }
  }));