import { configureStore } from '@reduxjs/toolkit';
import { AuthSlice } from './components/Auth';
import { AlertSlice } from './components/Alertbar';

const store = configureStore({
  reducer: {
    auth: AuthSlice.reducer,
    notifications: AlertSlice.reducer,
  },
  devTools: process.env.NODE_ENV !== 'production',
});

export default store;
