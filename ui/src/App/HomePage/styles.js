import { makeStyles } from '@material-ui/core/styles';
import mainTheme from '../../theme';

const drawerWidth = 240;

const useStyles = makeStyles(() => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: mainTheme.zIndex.drawer + 1,
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerContainer: {
    overflow: 'auto',
  },
  content: {
    flexGrow: 1,
    padding: mainTheme.spacing(3),
  },
}));

export default useStyles;
