import React, { useEffect, useState } from 'react';

import { useDispatch } from 'react-redux';
import { Button, CircularProgress, Grid, TextField } from '@mui/material';
import SaveIcon from '@mui/icons-material/Save';
import BasePage from 'App/BasePage';
import authApi from 'utils/authApi';
import { createAlert } from 'components/Alertbar';
import useStyles from './styles';

const ProfilePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [user, setUser] = useState({});
  const [isPerforming, setIsPerforming] = useState(false);

  useEffect(() => {
    authApi.get('user').then((result) => setUser(result.data));
  }, [dispatch]);

  const updateUser = () => {
    setIsPerforming(true);
    authApi
      .put('user', user)
      .then((result) => {
        setUser(result.data);
        dispatch(
          createAlert({
            message: `User info updated!`,
            type: 'success',
          }),
        );
      })
      .finally(() => setIsPerforming(false));
  };

  return (
    <BasePage>
      <form className={classes.root} noValidate autoComplete="off">
        <Grid container spacing={3}>
          <Grid item xs={12} sm={12} md={4}>
            <TextField
              id="userId"
              label="User id"
              type="number"
              fullWidth
              margin="normal"
              value={user.id || ''}
              variant="filled"
              InputProps={{
                readOnly: true,
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={4}>
            <TextField
              id="email"
              label="Email"
              type="email"
              fullWidth
              margin="normal"
              value={user.email || ''}
              variant="filled"
              InputProps={{
                readOnly: true,
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={4}>
            <TextField
              id="dateAdd"
              label="Date Add"
              type="date"
              fullWidth
              margin="normal"
              value={
                user.dateAdd
                  ? new Date(user.dateAdd).toISOString().substring(0, 10)
                  : ''
              }
              variant="filled"
              InputProps={{
                readOnly: true,
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="telegramChatId"
              label="Telegram Chat Id"
              type="number"
              required
              fullWidth
              margin="normal"
              value={user.telegramChatId || ''}
              variant="outlined"
              helperText="Example: 202081459"
              onChange={(e) => {
                setUser({ ...user, telegramChatId: e.target.value });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="tradingSymbol"
              label="Trading Symbol"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.tradingSymbol || ''}
              variant="outlined"
              helperText="Example: ETHUSDT"
              onChange={(e) => {
                setUser({ ...user, tradingSymbol: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="binanceApiKey"
              label="Binance Api Key"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.binanceApiKey || ''}
              variant="outlined"
              onChange={(e) => {
                setUser({ ...user, binanceApiKey: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="binanceSecretKey"
              label="Binance Secret Key"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.binanceSecretKey || ''}
              variant="outlined"
              onChange={(e) => {
                setUser({ ...user, binanceSecretKey: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="emailHost"
              label="Email Host"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.emailHost || ''}
              variant="outlined"
              helperText="Example: imap.gmail.com:993"
              onChange={(e) => {
                setUser({ ...user, emailHost: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="emailPassword"
              label="Email Password"
              type="password"
              required
              fullWidth
              margin="normal"
              value={user.emailPassword || ''}
              variant="outlined"
              onChange={(e) => {
                setUser({ ...user, emailPassword: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="emailUserName"
              label="Email Username"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.emailUsername || ''}
              variant="outlined"
              onChange={(e) => {
                setUser({ ...user, emailUsername: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={6}>
            <TextField
              id="emailFolder"
              label="Email Folder"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.emailFolder || ''}
              variant="outlined"
              helperText="Example: INBOX"
              onChange={(e) => {
                setUser({ ...user, emailFolder: e.target.value.trim() });
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={12}>
            <Button
              className={classes.submit}
              variant="contained"
              color="primary"
              fullWidth
              disabled={isPerforming}
              onClick={updateUser}
              startIcon={<SaveIcon />}
            >
              Save
              {isPerforming && (
                <CircularProgress
                  size={24}
                  className={classes.buttonProgress}
                />
              )}
            </Button>
          </Grid>
        </Grid>
      </form>
    </BasePage>
  );
};

export default ProfilePage;
