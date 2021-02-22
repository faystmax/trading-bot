import React, { useCallback, useMemo, useState } from 'react';
import { createBrowserHistory } from 'history';
import { Redirect, Route, Router, Switch } from 'react-router-dom';
import PrivateRoute from '../components/PrivateRoute';
import { AuthContext } from '../utils/auth';
import SignInPage from './SignInPage';
import HomePage from './HomePage';
import SignUpPage from './SignUpPage';

const hist = createBrowserHistory();

const App = () => {
  const existingAuth = JSON.parse(localStorage.getItem('auth'));
  const [auth, setAuth] = useState(existingAuth);

  const setAuthInfo = useCallback((data) => {
    localStorage.setItem('auth', JSON.stringify(data));
    setAuth(data);
  }, []);

  const authContextValue = useMemo(() => ({ auth, setAuth: setAuthInfo }), [
    auth,
    setAuthInfo,
  ]);

  return (
    <AuthContext.Provider value={authContextValue}>
      <Router history={hist}>
        <Switch>
          <Route path="/signIn" component={SignInPage} />
          <Route path="/signUp" component={SignUpPage} />
          <PrivateRoute exact path="/" component={HomePage} />
          <Redirect to="/" />
        </Switch>
      </Router>
    </AuthContext.Provider>
  );
};

export default App;
