import { makeStyles } from '@material-ui/core/styles';
import mainTheme from 'theme';

const drawerWidth = 240;

const useStyles = makeStyles(() => ({
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
  title: {
    flexGrow: 1,
  },
}));

export default useStyles;
