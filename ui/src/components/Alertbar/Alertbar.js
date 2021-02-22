import React, { useEffect, useState } from 'react';

import { Snackbar } from '@material-ui/core';
import { Alert } from '@material-ui/lab';
import { useSelector } from 'react-redux';

const Alertbar = () => {
  const { alerts } = useSelector((state) => state.notifications);
  const [alert, setAlert] = useState({ type: '', message: '' });
  const [show, setShow] = useState(false);

  useEffect(() => {
    if (alerts.length > 0) {
      setAlert(alerts[alerts.length - 1]);
      setShow(true);
      setTimeout(() => {
        setShow(false);
      }, 5000);
    }
  }, [alerts]);

  const onClose = () => {
    setShow(false);
  };

  return show ? (
    <Snackbar
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      open={show}
      autoHideDuration={6000}
      onClose={onClose}
    >
      <Alert onClose={onClose} severity={`${alert.type || 'error'}`}>
        {alert.message || ''}
      </Alert>
    </Snackbar>
  ) : null;
};

export default Alertbar;
