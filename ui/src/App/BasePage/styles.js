import { makeStyles } from '@material-ui/core/styles';
import mainTheme from 'theme';

const useStyles = makeStyles(() => ({
  root: {
    display: 'flex',
  },
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
    paddingTop: mainTheme.spacing(9),
    padding: mainTheme.spacing(3),
  },
}));

export default useStyles;
