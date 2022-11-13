import React from 'react';
import ReactDOM from 'react-dom';
import { ThemeProvider } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import { Provider } from 'react-redux';
import store from './store';
import App from './App/App';
import 'typeface-roboto';
import theme from './theme';
import Alert from './components/Alertbar/Alertbar';

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <CssBaseline />
        <Alert />
        <App />
      </Provider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById('root'),
);
