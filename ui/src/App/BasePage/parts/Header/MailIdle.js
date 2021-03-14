import React, { useState } from 'react';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import StopIcon from '@material-ui/icons/Stop';
import { IconButton } from '@material-ui/core';
import { useDispatch } from 'react-redux';
import SyncIcon from '@material-ui/icons/Sync';
import authApi from 'utils/authApi';
import { createAlert } from 'components/Alertbar';

const MailIdle = () => {
  const [isPerforming, setIsPerforming] = useState(false);
  const dispatch = useDispatch();

  const onSuccess = (data) => {
    dispatch(
      createAlert({
        message: `${data.data.message}`,
        type: 'success',
      }),
    );
  };

  const recreateMailIdle = () => {
    setIsPerforming(true);
    authApi
      .post('mailIdle/recreateAndStart')
      .then((data) => onSuccess(data))
      .finally(() => setIsPerforming(false));
  };

  const stopMailIdle = () => {
    setIsPerforming(true);
    authApi
      .post('mailIdle/stop')
      .then((data) => onSuccess(data))
      .finally(() => setIsPerforming(false));
  };

  const getStatus = () => {
    setIsPerforming(true);
    authApi
      .get('mailIdle/status')
      .then((data) => {
        dispatch(
          createAlert({
            message: `${data.data}`,
            type: 'info',
          }),
        );
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
