import React, { useEffect, useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useDispatch } from 'react-redux';
import { Button, CircularProgress, LinearProgress } from '@mui/material';
import RefreshIcon from '@mui/icons-material/Refresh';
import BasePage from 'App/BasePage';
import authApi from 'utils/authApi';
import { moneyFormat, priceFormat } from 'utils/currency';
import dateFormat from '../../utils/dateFormat';
import {
  GreedTableCell,
  RedTableCell,
  StyledTableCell,
  StyledTableRow,
} from './styles';

const OrdersPage = () => {
  const dispatch = useDispatch();
  const [orders, setOrders] = useState([]);
  const [isOrdersReloading, setIsOrdersReloading] = useState(false);
  const [isOrdersLoading, setIsOrdersLoading] = useState(false);

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

  return (
    <BasePage>
      <Button
        style={{ marginBottom: 10 }}
        variant="outlined"
        color="orange"
        disabled={isOrdersReloading}
        onClick={reloadOrders}
        startIcon={<RefreshIcon />}
      >
        Reload orders from Binance
        {isOrdersReloading && <CircularProgress size={24} />}
      </Button>
      {isOrdersLoading && <LinearProgress />}
      <TableContainer component={Paper}>
        <Table size="small" aria-label="Orders table" stickyHeader>
          <TableHead>
            <TableRow>
              <StyledTableCell align="left">Id</StyledTableCell>
              <StyledTableCell align="left">Symbol</StyledTableCell>
              <StyledTableCell align="right">Price</StyledTableCell>
              <StyledTableCell align="right">Quantity</StyledTableCell>
              <StyledTableCell align="right">
                Cumulative Quantity
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
                  {row.exchangeId}
                </StyledTableCell>
                <StyledTableCell align="right">{row.symbol}</StyledTableCell>
                <StyledTableCell align="right">
                  {priceFormat(row.realPrice)}
                </StyledTableCell>
                <StyledTableCell align="right">{row.origQty}</StyledTableCell>
                <StyledTableCell align="right">
                  {moneyFormat(row.cumulativeQuoteQty)}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {dateFormat(new Date(row.dateAdd))}
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
