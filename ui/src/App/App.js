import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import PrivateRoutes from '../components/PrivateRoute';
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
        <Route element={<PrivateRoutes />}>
          <Route path="/" element={<DealsPage />} />
          <Route path="/orders" element={<OrdersPage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/bot-config" element={<BotConfigPage />} />
          <Route path="/password" element={<ChangePasswordPage />} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
