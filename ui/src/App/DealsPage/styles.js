import { makeStyles, withStyles } from '@mui/material';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import mainTheme from '../../theme';

export const useStyles = makeStyles({
  customTableContainer: {
    overflowX: 'initial',
  },
});

export const StyledTableRow = withStyles(() => ({
  root: {
    // '&:nth-of-type(odd)': {
    //   backgroundColor: theme.palette.action.hover,
    // },
  },
}))(TableRow);

export const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: '#9aebff',
    color: theme.palette.common.black,
  },
  body: {
    fontSize: 14,
  },
  root: {
    borderWidth: 1,
    borderColor: 'rgb(161,161,161)',
    borderStyle: 'solid',
  },
}))(TableCell);

export const StickyTableHead = withStyles(() => ({
  root: {
    top: -mainTheme.spacing(1),
    left: 0,
    zIndex: 2,
    position: 'sticky',
  },
}))(TableHead);
