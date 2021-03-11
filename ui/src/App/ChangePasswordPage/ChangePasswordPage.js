import React, { useState } from 'react';

import { useDispatch } from 'react-redux';
import { Button, CircularProgress, Grid, TextField } from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import { useAuth } from 'hooks/useAuth';
import BasePage from 'App/BasePage';
import { alertActions } from 'components/Alertbar';
import api from 'utils/api';
import useStyles from './styles';

const ChangePasswordPage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { auth, logOut } = useAuth();
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newRepeatPassword, setNewRepeatPassword] = useState('');
  const [isPerforming, setIsPerforming] = useState(false);

  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    ...(auth && { Authorization: `${auth.type} ${auth.token}` }),
  };

  const updateUser = () => {
    setIsPerforming(true);
    if (newPassword !== newRepeatPassword) {
      dispatch(
        alertActions.createAlert({
          message: `New passwords are not equals!`,
          type: 'error',
        }),
      );
      setIsPerforming(false);
      return;
    }
    api({
      method: 'post',
      url: 'password',
      data: {
        oldPassword,
        newPassword,
      },
      headers,
    })
      .then(() => {
        setIsPerforming(false);
        dispatch(
          alertActions.createAlert({
            message: `Password successfully changed!`,
            type: 'success',
          }),
        );
      })
      .catch((error) => {
        if (!error.response) {
          dispatch(
            alertActions.createAlert({
              message: `Network error!`,
              type: 'error',
            }),
          );
        } else if (error.response.status === 401) {
          logOut();
        } else {
          dispatch(
            alertActions.createAlert({
              message: `Request error! ${error.response.status} ${error.response.data.error}`,
              type: 'error',
            }),
          );
        }
        setIsPerforming(false);
      });
  };

  return (
    <BasePage>
      <form className={classes.root} noValidate autoComplete="off">
        <Grid container spacing={3}>
          <Grid item xs={12} sm={12} md={12}>
            <TextField
              id="oldPassword"
              label="Old password"
              type="password"
              required
              fullWidth
              margin="normal"
              value={oldPassword}
              variant="outlined"
              onChange={(e) => {
                setOldPassword(e.target.value.trim());
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={12}>
            <TextField
              id="newPassword"
              label="New Password"
              type="password"
              required
              fullWidth
              margin="normal"
              value={newPassword}
              variant="outlined"
              onChange={(e) => {
                setNewPassword(e.target.value.trim());
              }}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={12}>
            <TextField
              id="newRepeatPassword"
              label="Repeat Password"
              type="password"
              required
              fullWidth
              margin="normal"
              value={newRepeatPassword}
              variant="outlined"
              onChange={(e) => {
                setNewRepeatPassword(e.target.value.trim());
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

export default ChangePasswordPage;
