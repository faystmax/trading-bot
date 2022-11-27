import { styled } from '@mui/material/styles';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

export const StyledTableRow = styled(TableRow)(({ theme }) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}));

export const StyledTableCell = styled(TableCell)(({ theme }) => ({
  head: {
    backgroundColor: '#b3b8ca',
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}));

export const RedTableCell = styled(TableCell)(() => ({
  body: {
    fontSize: 14,
    color: 'red',
  },
}));

export const GreedTableCell = styled(TableCell)(() => ({
  body: {
    fontSize: 14,
    color: 'green',
  },
}));
