import { makeStyles } from '@material-ui/core/styles';
import mainTheme from 'theme';

const drawerWidth = 240;

const useStyles = makeStyles(() => ({
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
    ...mainTheme.mixins.toolbar,
  },
  appBar: {
    zIndex: mainTheme.zIndex.drawer + 1,
    transition: mainTheme.transitions.create(['width', 'margin'], {
      easing: mainTheme.transitions.easing.sharp,
      duration: mainTheme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: mainTheme.transitions.create(['width', 'margin'], {
      easing: mainTheme.transitions.easing.sharp,
      duration: mainTheme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: mainTheme.transitions.create('width', {
      easing: mainTheme.transitions.easing.sharp,
      duration: mainTheme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: mainTheme.transitions.create('width', {
      easing: mainTheme.transitions.easing.sharp,
      duration: mainTheme.transitions.duration.leavingScreen,
    }),
    width: mainTheme.spacing(7),
    [mainTheme.breakpoints.up('sm')]: {
      width: mainTheme.spacing(9),
    },
  },
  title: {
    flexGrow: 1,
  },
  menuListItem: {
    [mainTheme.breakpoints.up('sm')]: {
      paddingLeft: mainTheme.spacing(3),
    },
  },
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
    padding: mainTheme.spacing(3),
  },
}));

export default useStyles;
