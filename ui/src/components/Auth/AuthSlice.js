import { createSlice } from '@reduxjs/toolkit';

const AuthSlice = createSlice({
  name: 'auth',
  initialState: JSON.parse(localStorage.getItem('auth')),
  reducers: {
    updateAuth: (state, action) => {
      localStorage.setItem('auth', JSON.stringify(action.payload));
      return action.payload;
    },
    emptyAuth: () => {
      localStorage.setItem('auth', JSON.stringify(null));
      return null;
    },
  },
});

export const { updateAuth, emptyAuth } = AuthSlice.actions;

export default AuthSlice;
