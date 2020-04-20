import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

import Container from '@material-ui/core/Container';
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

function SignInPage() {
  const classes = useStyles();

  return (
    <div>
      <Paper elevation={0} className={classes.background} />
      <Container component="main" maxWidth="xs">
        <Paper className={classes.signIn}>
          <Typography className={classes.label} component="h1" variant="h5">
            Trading Bot
          </Typography>
          <form className={classes.form} noValidate>
            <TextField
              required
              fullWidth
              id="login-input"
              variant="outlined"
              margin="normal"
              name="login"
              autoComplete="login"
              autoFocus
              label="Login"
            />
            <TextField
              required
              fullWidth
              variant="outlined"
              margin="normal"
              id="password-input"
              type="password"
              label="Password"
              autoComplete="current-password"
            />
            <Button
              className={classes.submit}
              variant="contained"
              color="primary"
              fullWidth
              type="submit"
            >
              Sign In
            </Button>
          </form>
        </Paper>
      </Container>
    </div>
  );
}

export default SignInPage;
