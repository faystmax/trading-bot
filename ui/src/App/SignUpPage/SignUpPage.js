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
import BackgroundImage from 'assets/background.jpg';
import { updateAuth } from '../../components/Auth';

const SignUpPage = () => {
  const location = useLocation();
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
    return <Navigate to={location.state?.from || '/'} replace />;
  }

  return (
    <div>
      <Paper
        elevation={0}
        sx={{
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
        }}
      />
      <Container component="main" maxWidth="xs">
        <Paper
          sx={{
            backgroundColor: '#fff',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            alignContent: 'center',
            justifyContent: 'center',
            marginTop: 7,
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography sx={{ m: 1 }} component="h1" variant="h5">
            Sign up in Trading Bot
          </Typography>
          {isError && <Alert severity="error">{errorMessage}</Alert>}
          <Box component="form" sx={{ mt: 0, mx: 3, mb: 3 }} noValidate>
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
              sx={{ mt: 3, my: 0, mb: 2 }}
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
                  sx={{
                    mt: -12,
                    ml: -12,
                    top: '50%',
                    left: '50%',
                    position: 'absolute',
                  }}
                />
              )}
            </Button>
            <Grid container justify="flex-end">
              <Grid item>
                <Link to="/signIn" variant="body2">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Paper>
        <Box mt={5}>
          <Copyright />
        </Box>
      </Container>
    </div>
  );
};

export default SignUpPage;
