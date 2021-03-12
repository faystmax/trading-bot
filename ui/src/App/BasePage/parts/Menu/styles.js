import { makeStyles } from '@material-ui/core/styles';
import mainTheme from 'theme';

const drawerWidth = 240;

const useStyles = makeStyles(() => ({
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
  menuListItem: {
    [mainTheme.breakpoints.up('sm')]: {
      paddingLeft: mainTheme.spacing(3),
    },
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...mainTheme.mixins.toolbar,
  },
}));

export default useStyles;
