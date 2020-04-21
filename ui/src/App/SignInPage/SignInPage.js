import React from 'react';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

import Container from '@material-ui/core/Container';
import Avatar from '@material-ui/core/Avatar';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import styles from './styles';

function SignInPage() {
  const classes = styles();

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
