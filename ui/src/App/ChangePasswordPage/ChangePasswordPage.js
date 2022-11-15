import React, { useState } from 'react';

import { useDispatch } from 'react-redux';
import { Button, CircularProgress, Grid, TextField } from '@mui/material';
import SaveIcon from '@mui/icons-material/Save';
import Box from '@mui/material/Box';
import BasePage from 'App/BasePage';
import { createAlert } from 'components/Alertbar';
import authApi from 'utils/authApi';

const ChangePasswordPage = () => {
  const dispatch = useDispatch();
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newRepeatPassword, setNewRepeatPassword] = useState('');
  const [isPerforming, setIsPerforming] = useState(false);

  const updateUser = () => {
    setIsPerforming(true);
    if (newPassword !== newRepeatPassword) {
      dispatch(
        createAlert({
          message: `New passwords are not equals!`,
          type: 'error',
        }),
      );
      setIsPerforming(false);
      return;
    }
    authApi
      .post('password', { oldPassword, newPassword })
      .then(() => {
        dispatch(
          createAlert({
            message: `Password successfully changed!`,
            type: 'success',
          }),
        );
      })
      .finally(() => setIsPerforming(false));
  };

  return (
    <BasePage>
      <Box
        component="form"
        sx={{ display: 'flex' }}
        noValidate
        autoComplete="off"
      >
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
              variant="contained"
              color="primary"
              fullWidth
              disabled={isPerforming}
              onClick={updateUser}
              startIcon={<SaveIcon />}
            >
              Save
              {isPerforming && <CircularProgress size={24} />}
            </Button>
          </Grid>
        </Grid>
      </Box>
    </BasePage>
  );
};

export default ChangePasswordPage;
