import React, { useEffect, useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useDispatch } from 'react-redux';
import BasePage from 'App/BasePage';
import authApi from 'utils/authApi';
import currencyFormat from 'utils/currency';
import { StyledTableCell, StyledTableRow, useStyles } from './styles';

const HomePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    authApi.get('orders').then((result) => setOrders(result.data));
  }, [dispatch]);

  return (
    <BasePage>
      <TableContainer component={Paper}>
        <Table className={classes.table} size="small" aria-label="Orders table">
          <TableHead>
            <TableRow>
              <StyledTableCell align="right">Price</StyledTableCell>
              <StyledTableCell align="right">OrigQty</StyledTableCell>
              <StyledTableCell align="right">Total value</StyledTableCell>
              <StyledTableCell align="right">Date Add</StyledTableCell>
              <StyledTableCell align="right">Side</StyledTableCell>
              <StyledTableCell align="right">OrderType</StyledTableCell>
              <StyledTableCell align="right">Status</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((row) => (
              <StyledTableRow key={row.id}>
                <StyledTableCell align="right">
                  {currencyFormat(row.realPrice)}
                </StyledTableCell>
                <StyledTableCell align="right">{row.origQty}</StyledTableCell>
                <StyledTableCell align="right">
                  {currencyFormat(row.cumulativeQuoteQty)}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {new Date(row.dateAdd).toUTCString()}
                </StyledTableCell>
                <StyledTableCell align="right">{row.side}</StyledTableCell>
                <StyledTableCell align="right">{row.type}</StyledTableCell>
                <StyledTableCell align="right">{row.status}</StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </BasePage>
  );
};

export default HomePage;
