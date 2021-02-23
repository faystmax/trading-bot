import React, { useCallback, useEffect, useState } from 'react';

import { useDispatch } from 'react-redux';
import { Grid, TextField } from '@material-ui/core';
import { useAuth } from 'utils/auth';
import BasePage from 'components/BasePage';
import { alertActions } from 'components/Alertbar';
import api from 'utils/api';
import { useStyles } from './styles';

const ProfilePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { auth, setAuth } = useAuth();
  const [user, setUser] = useState({});

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

  useEffect(() => {
    const headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      ...(auth && { Authorization: `${auth.type} ${auth.token}` }),
    };

    api({
      method: 'get',
      url: 'user',
      headers,
    })
      .then((result) => {
        setUser(result.data);
      })
      .catch((error) => {
        if (error.response.status === 401) {
          logOut();
        } else {
          dispatch(
            alertActions.createAlert({
              message: `Request error! ${error.response.status} ${error.response.data.error}`,
              type: 'error',
            }),
          );
        }
      });
  }, [auth, logOut, dispatch]);

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
              id="emailFolder"
              label="Email Folder"
              type="text"
              required
              fullWidth
              margin="normal"
              value={user.emailFolder || ''}
              variant="outlined"
              onChange={(e) => {
                setUser({ ...user, emailFolder: e.target.value.trim() });
              }}
            />
          </Grid>
        </Grid>
      </form>
    </BasePage>
  );
};

export default ProfilePage;
