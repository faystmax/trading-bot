import React, { useCallback } from 'react';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import StopIcon from '@material-ui/icons/Stop';
import { IconButton } from '@material-ui/core';
import { useDispatch } from 'react-redux';
import api from 'utils/api';
import { useAuth } from 'utils/auth';
import { alertActions } from '../Alertbar';

const MailIdle = () => {
  const { auth, setAuth } = useAuth();
  const dispatch = useDispatch();
  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    ...(auth && { Authorization: `${auth.type} ${auth.token}` }),
  };

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

  const startMailIdle = () => {
    api({
      method: 'post',
      url: 'mailIdle/start',
      headers,
    })
      .then((data) => {
        dispatch(
          alertActions.createAlert({
            message: `${data.data.message}`,
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
      });
  };

  const stopMailIdle = () => {
    api({
      method: 'post',
      url: 'mailIdle/stop',
      headers,
    })
      .then((data) => {
        dispatch(
          alertActions.createAlert({
            message: `${data.data.message}`,
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
      });
  };

  return (
    <>
      <IconButton
        aria-label="Start Mail Idle"
        onClick={startMailIdle}
        color="inherit"
        size="medium"
      >
        <PlayArrowIcon />
      </IconButton>
      <IconButton
        aria-label="Stop Mail Idle"
        onClick={stopMailIdle}
        color="inherit"
        size="medium"
      >
        <StopIcon />
      </IconButton>
    </>
  );
};

export default MailIdle;
