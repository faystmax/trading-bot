import React, { useCallback, useEffect, useState } from 'react';

import { useDispatch } from 'react-redux';
import { TextField } from '@material-ui/core';
import { useAuth } from 'utils/auth';
import BasePage from 'components/BasePage';
import { alertActions } from 'components/Alertbar';
import api from 'utils/api';
import { useStyles } from './styles';

const ProfilePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { auth, setAuth } = useAuth();
  const [user, setUser] = useState([]);

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
        <TextField id="standard-basic" label="Standard" value={user.email} />
        <TextField id="filled-basic" label="Filled" variant="filled" />
        <TextField id="outlined-basic" label="Outlined" variant="outlined" />
      </form>
    </BasePage>
  );
};

export default ProfilePage;
