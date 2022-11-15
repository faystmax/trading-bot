import { styled } from '@mui/material/styles';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import mainTheme from '../../theme';

export const StyledTableRow = styled(TableRow)(() => ({
  root: {
    // '&:nth-of-type(odd)': {
    //   backgroundColor: theme.palette.action.hover,
    // },
  },
}));

export const StyledTableCell = styled(TableCell)((theme) => ({
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
}));

export const StickyTableHead = styled(TableHead)(() => ({
  root: {
    top: -mainTheme.spacing(1),
    left: 0,
    zIndex: 2,
    position: 'sticky',
  },
}));
