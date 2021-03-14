import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';
import {
  Avatar,
  Box,
  Button,
  CircularProgress,
  Container,
  Grid,
  Paper,
  TextField,
  Typography,
} from '@material-ui/core';
import { LockOutlined as LockOutlinedIcon } from '@material-ui/icons';
import { Alert } from '@material-ui/lab';
import Link from '@material-ui/core/Link';
import { useDispatch, useSelector } from 'react-redux';
import api from 'utils/defaultApi';
import Copyright from 'components/Copyright';
import { updateAuth } from '../../components/Auth';
import useStyles from './styles';

const SignUpPage = (props) => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [isError, setIsError] = useState(false);
  const [isPerforming, setIsPerforming] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const auth = useSelector((state) => state.auth);

  const signUpRequest = () => {
    if (password !== repeatPassword) {
      setIsError(true);
      setErrorMessage('Passwords are not equals!');
      return;
    }
    setIsError(false);
    setErrorMessage('');
    setIsPerforming(true);
    api
      .post('auth/signUp', { email, password })
      .then((result) => {
        dispatch(updateAuth(result.data));
      })
      .catch((error) => {
        if (!error.response) {
          setErrorMessage('Network error!');
        } else {
          setErrorMessage(error.response.data.message);
        }
        setIsError(true);
      })
      .finally(() => setIsPerforming(false));
  };

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
            Sign up in Trading Bot
          </Typography>
          {isError && <Alert severity="error">{errorMessage}</Alert>}
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
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
            <TextField
              required
              fullWidth
              variant="outlined"
              margin="normal"
              id="password-input-repeat"
              type="password"
              label="Repeat Password"
              value={repeatPassword}
              onChange={(e) => {
                setRepeatPassword(e.target.value);
              }}
            />
            <Button
              className={classes.submit}
              variant="contained"
              color="primary"
              fullWidth
              disabled={isPerforming}
              onClick={signUpRequest}
            >
              Sign up new user
              {isPerforming && (
                <CircularProgress
                  size={24}
                  className={classes.buttonProgress}
                />
              )}
            </Button>
            <Grid container justify="flex-end">
              <Grid item>
                <Link href="/signIn" variant="body2">
                  Already have an account? Sign in
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
};

export default SignUpPage;
