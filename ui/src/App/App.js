import React, { useState } from 'react';
import { createBrowserHistory } from 'history';
import { Route, BrowserRouter as Router } from 'react-router-dom';
import PrivateRoute from '../components/PrivateRoute';
import { AuthContext } from '../utils/auth';
import SignInPage from './SignInPage';
import HomePage from './HomePage';

const hist = createBrowserHistory();

function App() {
  const existingAuth = JSON.parse(localStorage.getItem('auth'));
  const [auth, setAuth] = useState(existingAuth);

  const setAuthInfo = (data) => {
    localStorage.setItem('auth', JSON.stringify(data));
    setAuth(data);
  };

  return (
    <AuthContext.Provider value={{ auth, setAuth: setAuthInfo }}>
      <Router history={hist}>
        <Route exact path="/signIn" component={SignInPage} />
        <PrivateRoute exact path="/" component={HomePage} />
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
