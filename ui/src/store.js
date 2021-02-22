import { configureStore } from '@reduxjs/toolkit';
import { AlertSlice } from './components/Alertbar';

const reducer = {
  notifications: AlertSlice.reducer,
};

const store = configureStore({
  reducer,
  devTools: process.env.NODE_ENV !== 'production',
});

export default store;
