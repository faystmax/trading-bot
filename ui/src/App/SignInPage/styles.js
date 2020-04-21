import { makeStyles } from '@material-ui/core/styles';
import BackgroundImage from '../../assets/background.jpg';

const useStyles = makeStyles((theme) => ({
  background: {
    backgroundImage: `url(${BackgroundImage})`,
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    filter: 'blur(4px)',
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
    marginTop: theme.spacing(7),
  },
  label: {
    margin: theme.spacing(3),
  },
  form: {
    margin: theme.spacing(0, 3, 3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default useStyles;
