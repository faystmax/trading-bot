import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';
import {
  Avatar,
  Box,
  Button,
  CircularProgress,
  Container,
  Paper,
  TextField,
  Typography,
} from '@material-ui/core';
import { LockOutlined as LockOutlinedIcon } from '@material-ui/icons';
import { Alert } from '@material-ui/lab';
import { useAuth } from 'utils/auth';
import api from 'utils/api';
import Copyright from 'components/Copyright';
import useStyles from './styles';

const SignInPage = (props) => {
  const classes = useStyles();
  const [isError, setIsError] = useState(false);
  const [isPerforming, setIsPerforming] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { auth, setAuth } = useAuth();

  const loginRequest = () => {
    setIsPerforming(true);
    api({
      method: 'post',
      url: 'auth/signIn',
      data: {
        email,
        password,
      },
    })
      .then((result) => {
        setAuth(result.data);
        setIsPerforming(false);
      })
      .catch(() => {
        setIsError(true);
        setIsPerforming(false);
      });
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
