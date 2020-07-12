import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import Avatar from '@material-ui/core/Avatar';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Box from '@material-ui/core/Box';
import Alert from '@material-ui/lab/Alert';
import Grid from '@material-ui/core/Grid';
import Link from '@material-ui/core/Link';
import api from '../../utils/api';
import Copyright from '../../components/Copyright';
import { useAuth } from '../../utils/auth';
import useStyles from './styles';

function SignInPage(props) {
  const classes = useStyles();
  const [isError, setIsError] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { auth, setAuth } = useAuth();

  function loginRequest() {
    api({
      method: 'post',
      url: 'auth/signIn',
      data: {
        email,
        password,
      },
    })
      .then((result) => {
        if (result.status === 200) {
          setAuth(result.data);
        } else {
          setIsError(true);
        }
      })
      .catch(() => {
        setIsError(true);
      });
  }

  if (auth) {
    return <Redirect to={props.location.state?.referer || '/'} />;
  }

  return (
    <div>
      <Paper elevation={0} className={classes.background} />
      <Container component="main" maxWidth="xs">
        <Paper className={classes.signIn}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography className={classes.label} component="h1" variant="h5">
            Trading Bot
          </Typography>
          {isError && (
            <Alert severity="error">
              The username or password provided were incorrect!
            </Alert>
          )}
          <form className={classes.form} noValidate>
            <TextField
              required
              fullWidth
              id="login-input"
              variant="outlined"
              margin="normal"
              name="email"
              type="email"
              autoComplete="email"
              autoFocus
              label="Email"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
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
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
            <Button
              className={classes.submit}
              variant="contained"
              color="primary"
              fullWidth
              onClick={loginRequest}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                {/* TODO */}
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                {/* TODO */}
                <Link href="#" variant="body2">
                  First time here? Sign Up
                </Link>
              </Grid>
            </Grid>
          </form>
        </Paper>
        <Box mt={5}>
          <Copyright />
        </Box>
      </Container>
    </div>
  );
}

export default SignInPage;
