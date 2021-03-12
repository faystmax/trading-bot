import React, { useCallback, useState } from 'react';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import StopIcon from '@material-ui/icons/Stop';
import { IconButton } from '@material-ui/core';
import { useDispatch } from 'react-redux';
import SyncIcon from '@material-ui/icons/Sync';
import api from 'utils/api';
import { useAuth } from 'hooks/useAuth';
import { alertActions } from 'components/Alertbar';

const MailIdle = () => {
  const { auth, setAuth } = useAuth();
  const [isPerforming, setIsPerforming] = useState(false);
  const dispatch = useDispatch();
  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    ...(auth && { Authorization: `${auth.type} ${auth.token}` }),
  };

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

  const recreateMailIdle = () => {
    setIsPerforming(true);
    api({
      method: 'post',
      url: 'mailIdle/recreateAndStart',
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
      })
      .finally(() => setIsPerforming(false));
  };

  const stopMailIdle = () => {
    setIsPerforming(true);
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
      })
      .finally(() => setIsPerforming(false));
  };

  const getStatus = () => {
    setIsPerforming(true);
    api({
      method: 'get',
      url: 'mailIdle/status',
      headers,
    })
      .then((data) => {
        dispatch(
          alertActions.createAlert({
            message: `${data.data}`,
            type: 'info',
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
      })
      .finally(() => setIsPerforming(false));
  };

  return (
    <>
      <IconButton
        aria-label="Recreate Mail Idle"
        onClick={recreateMailIdle}
        disabled={isPerforming}
        color="inherit"
        size="medium"
      >
        <PlayArrowIcon />
      </IconButton>
      <IconButton
        aria-label="Stop Mail Idle"
        onClick={stopMailIdle}
        disabled={isPerforming}
        color="inherit"
        size="medium"
      >
        <StopIcon />
      </IconButton>
      <IconButton
        aria-label="Get status"
        onClick={getStatus}
        disabled={isPerforming}
        color="inherit"
        size="medium"
      >
        <SyncIcon />
      </IconButton>
    </>
  );
};

export default MailIdle;
