import React from 'react';
import { createBrowserHistory } from 'history';
import { Redirect, Route, Router, Switch } from 'react-router-dom';
import PrivateRoute from '../components/PrivateRoute';
import SignInPage from './SignInPage';
import OrdersPage from './OrdersPage';
import SignUpPage from './SignUpPage';
import ProfilePage from './ProfilePage';
import ChangePasswordPage from './ChangePasswordPage';
import DealsPage from './DealsPage';

const hist = createBrowserHistory();

const App = () => {
  return (
    <Router history={hist}>
      <Switch>
        <Route path="/signIn" component={SignInPage} />
        <Route path="/signUp" component={SignUpPage} />
        <PrivateRoute exact path="/" component={DealsPage} />
        <PrivateRoute exact path="/orders" component={OrdersPage} />
        <PrivateRoute exact path="/profile" component={ProfilePage} />
        <PrivateRoute exact path="/password" component={ChangePasswordPage} />
        <Redirect to="/" />
      </Switch>
    </Router>
  );
};

export default App;
