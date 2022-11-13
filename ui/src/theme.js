import { red } from '@mui/material/colors';
import { createTheme } from '@mui/material';

const theme = createTheme({
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
