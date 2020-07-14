import { makeStyles } from '@material-ui/core/styles';
import BackgroundImage from 'assets/background.jpg';
import mainTheme from 'theme';

const useStyles = makeStyles(() => ({
  background: {
    backgroundImage: `url(${BackgroundImage})`,
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    height: '100%',
    width: '100%',
    position: 'absolute',
    top: '0',
    left: '0',
    zIndex: '-1',
  },
  signIn: {
    backgroundColor: '#fff',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    alignContent: 'center',
    justifyContent: 'center',
    marginTop: mainTheme.spacing(7),
  },
  avatar: {
    margin: mainTheme.spacing(1),
    backgroundColor: mainTheme.palette.secondary.main,
  },
  label: {
    margin: mainTheme.spacing(1),
  },
  form: {
    margin: mainTheme.spacing(0, 3, 3),
  },
  submit: {
    margin: mainTheme.spacing(3, 0, 2),
  },
}));

export default useStyles;
