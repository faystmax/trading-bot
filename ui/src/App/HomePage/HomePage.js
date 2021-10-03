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
import {
  GreedTableCell,
  RedTableCell,
  StyledTableCell,
  StyledTableRow,
  useStyles,
} from './styles';

const HomePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [orders, setOrders] = useState([]);
  const [totalAmount, setTotalAmount] = useState();

  useEffect(() => {
    authApi.get('orders').then((result) => setOrders(result.data));

    const interval = setInterval(() => {
      authApi
        .get('binance/total')
        .then((result) => setTotalAmount(result.data));
    }, 10 * 1000);
    return () => clearInterval(interval);
  }, [dispatch]);

  return (
    <BasePage>
      <TableContainer component={Paper}>
        <Table className={classes.table} size="small" aria-label="Orders table">
          <TableHead>
            <TableRow>
              <StyledTableCell align="left">Symbol</StyledTableCell>
              <StyledTableCell align="right">Price</StyledTableCell>
              <StyledTableCell align="right">OrigQty</StyledTableCell>
              <StyledTableCell align="right">
                Cumulative Quote Qty
              </StyledTableCell>
              <StyledTableCell align="right">Date Add</StyledTableCell>
              <StyledTableCell align="right">Side</StyledTableCell>
              <StyledTableCell align="right">OrderType</StyledTableCell>
              <StyledTableCell align="right">Status</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((row) => (
              <StyledTableRow key={row.id}>
                <StyledTableCell component="th" scope="row">
                  {row.symbol}
                </StyledTableCell>
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
                {row.side === 'BUY' ? (
                  <GreedTableCell align="right">{row.side}</GreedTableCell>
                ) : (
                  <RedTableCell align="right">{row.side}</RedTableCell>
                )}
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
