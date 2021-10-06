import { makeStyles, withStyles } from '@material-ui/core/styles';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';

export const useStyles = makeStyles(null);

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
