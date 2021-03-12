import { createSlice } from '@reduxjs/toolkit';

const AlertSlice = createSlice({
  name: 'alert',
  initialState: {
    alerts: [],
  },
  reducers: {
    createAlert: (state, action) => {
      state.alerts.push({
        message: action.payload.message,
        type: action.payload.type,
      });
    },
  },
});

export const { createAlert } = AlertSlice.actions;

export default AlertSlice;
