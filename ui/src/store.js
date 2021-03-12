import { configureStore } from '@reduxjs/toolkit';
import { AlertSlice } from './components/Alertbar';

const store = configureStore({
  reducer: {
    notifications: AlertSlice.reducer,
  },
  devTools: process.env.NODE_ENV !== 'production',
});

export default store;
