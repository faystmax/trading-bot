import React from 'react';
import { createBrowserHistory } from 'history';
import { Route, Router, Switch } from 'react-router-dom';
import Auth from './Auth/Auth';

const hist = createBrowserHistory();

function App() {
  return (
    <Router history={hist}>
      <Switch>
        {/* <Route path="/admin" component={Admin} /> */}
        <Route path="/" component={Auth} />
      </Switch>
    </Router>
  );
}

export default App;
