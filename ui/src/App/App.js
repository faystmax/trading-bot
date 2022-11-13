import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import PrivateRoute from '../components/PrivateRoute';
import SignInPage from './SignInPage';
import OrdersPage from './OrdersPage';
import SignUpPage from './SignUpPage';
import ProfilePage from './ProfilePage';
import ChangePasswordPage from './ChangePasswordPage';
import DealsPage from './DealsPage';
import BotConfigPage from './BotConfigPage';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/signIn" element={<SignInPage />} />
        <Route path="/signUp" element={<SignUpPage />} />
        <PrivateRoute path="/" element={DealsPage} />
        <PrivateRoute path="/orders" element={OrdersPage} />
        <PrivateRoute path="/profile" element={ProfilePage} />
        <PrivateRoute path="/bot-config" element={BotConfigPage} />
        <PrivateRoute path="/password" element={ChangePasswordPage} />
        <Navigate to="/" replace />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
