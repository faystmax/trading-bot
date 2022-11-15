import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';

const authSelector = (state) => state.auth;

const PrivateRoutes = () => {
  const auth = useSelector(authSelector);
  const location = useLocation();

  return auth ? (
    <Outlet />
  ) : (
    <Navigate to="/signIn" state={{ from: location }} replace />
  );
};

export default PrivateRoutes;
