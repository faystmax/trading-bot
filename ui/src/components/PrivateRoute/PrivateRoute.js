import React from 'react';
import { Navigate, Route, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';

const authSelector = (state) => state.auth;

const PrivateRoute = ({ element: Element, ...rest }) => {
  const auth = useSelector(authSelector);
  const location = useLocation();

  return (
    <Route
      {...rest}
      element={
        auth ? (
          <Element />
        ) : (
          <Navigate to="/signIn" state={{ from: location }} replace />
        )
      }
    />
  );
};

export default PrivateRoute;
