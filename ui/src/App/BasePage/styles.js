import makeStyles from '@mui/styles/makeStyles';
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
