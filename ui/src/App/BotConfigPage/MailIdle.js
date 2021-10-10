import React, { useState } from 'react';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';
import StopIcon from '@material-ui/icons/Stop';
import { Button } from '@material-ui/core';
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
      <Button
        onClick={recreateMailIdle}
        disabled={isPerforming}
        color="inherit"
        size="medium"
        variant="contained"
        endIcon={<PlayArrowIcon />}
        style={{ margin: '8px' }}
      >
        Recreate Mail Idle
      </Button>
      <Button
        onClick={stopMailIdle}
        disabled={isPerforming}
        color="inherit"
        size="medium"
        variant="contained"
        endIcon={<StopIcon />}
        style={{ margin: '8px' }}
      >
        Stop Mail Idle
      </Button>
      <Button
        onClick={getStatus}
        disabled={isPerforming}
        color="inherit"
        size="medium"
        variant="contained"
        endIcon={<SyncIcon />}
        style={{ margin: '8px' }}
      >
        <SyncIcon />
        Get status
      </Button>
    </>
  );
};

export default MailIdle;
