import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { useAuth } from 'hooks/useAuth';

const PrivateRoute = ({ component: Component, ...rest }) => {
  const { auth } = useAuth();

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
