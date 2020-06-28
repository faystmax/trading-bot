import React from 'react';
import { createBrowserHistory } from 'history';
import { Route, Router, Switch } from 'react-router-dom';
import SignInPage from './SignInPage';
import HomePage from './HomePage';

const hist = createBrowserHistory();

function App() {
  return (
    <Router history={hist}>
      <Switch>
        {/* <Route path="/admin" component={Admin} /> */}
        <Route path="/" component={SignInPage} />
        <Route path="/admin" component={HomePage} />
      </Switch>
    </Router>
  );
}

export default App;
