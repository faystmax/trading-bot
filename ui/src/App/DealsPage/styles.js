import { makeStyles, withStyles } from '@material-ui/core/styles';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';

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
    top: 0,
    left: 0,
    zIndex: 2,
    position: 'sticky',
  },
}))(TableHead);
