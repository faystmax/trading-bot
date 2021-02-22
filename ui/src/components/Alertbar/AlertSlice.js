import { createSlice } from '@reduxjs/toolkit';

export const AlertSlice = createSlice({
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

export const { actions } = AlertSlice;

export default AlertSlice;
