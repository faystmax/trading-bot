import { red } from '@material-ui/core/colors';
import { createMuiTheme } from '@material-ui/core/styles';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#556cd6',
    },
    secondary: {
      main: '#72a2ee',
    },
    error: {
      main: red.A400,
    },
    background: {
      default: '#fff',
    },
    orange: {
      main: '#faa775',
      contrastText: '#fff',
    },
  },
});

export default theme;
