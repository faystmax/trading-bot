import React, { useEffect, useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useDispatch } from 'react-redux';
import { Button, CircularProgress, LinearProgress } from '@material-ui/core';
import RefreshIcon from '@material-ui/icons/Refresh';
import BasePage from 'App/BasePage';
import authApi from 'utils/authApi';
import { moneyFormat, priceFormat } from 'utils/currency';
import {
  GreedTableCell,
  RedTableCell,
  StyledTableCell,
  StyledTableRow,
  useStyles,
} from './styles';

const OrdersPage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [orders, setOrders] = useState([]);
  const [isOrdersReloading, setIsOrdersReloading] = useState(false);
  const [isOrdersLoading, setIsOrdersLoading] = useState(false);
  const [isActiveSymbolsReloading, setIsActiveSymbolsReloading] = useState(
    false,
  );

  useEffect(() => {
    setIsOrdersLoading(true);
    authApi
      .get('orders')
      .then((result) => setOrders(result.data))
      .finally(() => setIsOrdersLoading(false));
  }, [dispatch]);

  const reloadOrders = () => {
    setIsOrdersReloading(true);
    setIsOrdersLoading(true);
    authApi
      .post('orders/reload')
      .then((result) => setOrders(result.data))
      .finally(() => {
        setIsOrdersReloading(false);
        setIsOrdersLoading(false);
      });
  };

  const reloadActiveSymbols = () => {
    setIsActiveSymbolsReloading(true);
    authApi
      .post('binance/symbols/reload')
      .finally(() => setIsActiveSymbolsReloading(false));
  };

  return (
    <BasePage>
      <Button
        style={{ marginBottom: 10 }}
        className={classes.submit}
        variant="outlined"
        color="orange"
        disabled={isOrdersReloading}
        onClick={reloadOrders}
        startIcon={<RefreshIcon />}
      >
        Reload orders from Binance
        {isOrdersReloading && (
          <CircularProgress size={24} className={classes.buttonProgress} />
        )}
      </Button>
      <Button
        style={{ marginBottom: 10, marginLeft: 10 }}
        className={classes.submit}
        variant="outlined"
        color="orange"
        disabled={isActiveSymbolsReloading}
        onClick={reloadActiveSymbols}
        startIcon={<RefreshIcon />}
      >
        Reload active symbols
        {isActiveSymbolsReloading && (
          <CircularProgress size={24} className={classes.buttonProgress} />
        )}
      </Button>
      {isOrdersLoading && <LinearProgress />}
      <TableContainer component={Paper}>
        <Table
          className={classes.table}
          size="small"
          aria-label="Orders table"
          stickyHeader
        >
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
            {!isOrdersLoading && orders.length === 0 && (
              <StyledTableRow>
                <StyledTableCell colSpan={8} align="center">
                  Order list is empty!
                </StyledTableCell>
              </StyledTableRow>
            )}
            {orders.map((row) => (
              <StyledTableRow key={row.id}>
                <StyledTableCell component="th" scope="row">
                  {row.symbol}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {priceFormat(row.realPrice)}
                </StyledTableCell>
                <StyledTableCell align="right">{row.origQty}</StyledTableCell>
                <StyledTableCell align="right">
                  {moneyFormat(row.cumulativeQuoteQty)}
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

export default OrdersPage;
