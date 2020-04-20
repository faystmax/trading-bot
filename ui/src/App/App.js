import React from 'react';
import { createBrowserHistory } from 'history';
import { Route, Router, Switch } from 'react-router-dom';
import SignInPage from './SignInPage/SignInPage';

const hist = createBrowserHistory();

function App() {
  return (
    <Router history={hist}>
      <Switch>
        {/* <Route path="/admin" component={Admin} /> */}
        <Route path="/" component={SignInPage} />
      </Switch>
    </Router>
  );
}

export default App;
