import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { useSelector } from 'react-redux';

const authSelector = (state) => state.auth;

const PrivateRoute = ({ component: Component, ...rest }) => {
  const auth = useSelector(authSelector);

  return (
    <Route
      {...rest}
      render={(props) =>
        auth ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{ pathname: '/signIn', state: { referer: props.location } }}
          />
        )
      }
    />
  );
};

export default PrivateRoute;
