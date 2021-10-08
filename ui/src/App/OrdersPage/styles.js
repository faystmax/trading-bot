import { makeStyles, withStyles } from '@material-ui/core/styles';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';

export const useStyles = makeStyles(null);

export const StyledTableRow = withStyles((theme) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}))(TableRow);

export const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: '#b3b8ca',
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

export const RedTableCell = withStyles(() => ({
  body: {
    fontSize: 14,
    color: 'red',
  },
}))(TableCell);

export const GreedTableCell = withStyles(() => ({
  body: {
    fontSize: 14,
    color: 'green',
  },
}))(TableCell);
