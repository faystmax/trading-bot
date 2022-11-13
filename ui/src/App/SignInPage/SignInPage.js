import React, { useState } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
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
} from '@mui/material';
import { LockOutlined as LockOutlinedIcon } from '@mui/icons-material';
import Alert from '@mui/material/Alert';
import Link from '@mui/material/Link';
import { useDispatch, useSelector } from 'react-redux';
import api from 'utils/defaultApi';
import Copyright from 'components/Copyright';
import { updateAuth } from '../../components/Auth';
import useStyles from './styles';

const SignInPage = () => {
  const classes = useStyles();
  const location = useLocation();
  const dispatch = useDispatch();
  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isPerforming, setIsPerforming] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const auth = useSelector((state) => state.auth);

  const loginRequest = () => {
    setIsPerforming(true);
    api
      .post('auth/signIn', { email, password })
      .then((result) => {
        dispatch(updateAuth(result.data));
        setIsPerforming(false);
      })
      .catch((error) => {
        if (!error.response) {
          setErrorMessage('Network error!');
        } else if (error.response.status === 401) {
          setErrorMessage('The username or password provided were incorrect!');
        } else {
          setErrorMessage(error.response.data.message || 'Unknown error!');
        }
        setIsError(true);
        setIsPerforming(false);
      });
  };

  if (auth) {
    return <Navigate to={location.state?.from || '/'} replace />;
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
              disabled={isPerforming}
              onClick={loginRequest}
            >
              Sign In
              {isPerforming && (
                <CircularProgress
                  size={24}
                  className={classes.buttonProgress}
                />
              )}
            </Button>
            <Grid container justify="flex-end">
              <Grid item>
                <Link href="/signUp" variant="body2">
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
};

export default SignInPage;
