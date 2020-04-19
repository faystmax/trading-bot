import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import FormLabel from '@material-ui/core/FormLabel';
import BackgroundImage from '../../assets/background.jpg';

const useStyles = makeStyles(() => ({
  root: {
    backgroundImage: `url(${BackgroundImage})`,
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    filter: 'blur(5px)',
    height: '100vh',
    width: '100%',
    position: 'fixed',
  },
  auth: {
    backgroundColor: '#f2f8f8',
    position: 'fixed',
    left: '50%',
    top: '50%',
    transform: 'translate(-50%, -50%)',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    alignContent: 'center',
    justifyContent: 'center',
    margin: '1rem 2.5rem',
  },
  formItem: {
    margin: '1rem',
  },
}));

function Auth() {
  const classes = useStyles();

  return (
    <div>
      <Paper elevation={0} className={classes.root} />
      <Paper className={classes.auth}>
        <form className={classes.form} noValidate>
          <FormLabel className={classes.formItem}>Trading Bot</FormLabel>
          <TextField
            className={classes.formItem}
            required
            id="login-input"
            label="Login"
          />
          <TextField
            required
            className={classes.formItem}
            id="password-input"
            type="password"
            label="Password"
            autoComplete="current-password"
          />
          <Button
            className={classes.formItem}
            variant="contained"
            color="primary"
          >
            Log in
          </Button>
        </form>
      </Paper>
    </div>
  );
}

export default Auth;
