import React, { useEffect, useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useDispatch } from 'react-redux';
import { LinearProgress, Typography } from '@material-ui/core';
import BasePage from 'App/BasePage';
import { moneyFormat } from 'utils/currency';
import authApi from 'utils/authApi';
import {
  StickyTableHead,
  StyledTableCell,
  StyledTableRow,
  useStyles,
} from './styles';
import DealRow from './DealRow';

const DealsPage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [latestPrices, setLatestPrices] = useState(null);
  const [deals, setDeals] = useState([]);
  const [isDealsLoading, setIsDealsLoading] = useState(false);

  const callLatestPrices = () => {
    authApi.get('binance/price').then((result) => {
      const map = new Map(
        result.data.map((ticket) => [ticket.symbol, ticket.price]),
      );
      setLatestPrices(map);
    });
  };

  useEffect(() => {
    setIsDealsLoading(true);
    authApi
      .get('deals')
      .then((result) => setDeals(result.data))
      .finally(() => setIsDealsLoading(false));

    callLatestPrices();
    const interval = setInterval(() => callLatestPrices(), 10 * 1000);
    return () => clearInterval(interval);
  }, [dispatch]);

  const getLatestPrice = (symbol) => {
    return latestPrices !== null ? latestPrices.get(symbol) : '';
  };

  return (
    <BasePage>
      <Typography
        style={{ paddingRight: 10 }}
        variant="h5"
        align="right"
        noWrap
      >
        Total profit:{' '}
        {moneyFormat(
          deals
            .flatMap((deal) => deal.sellOrders)
            .reduce((a, b) => a + b.dealProfit, 0),
        )}
      </Typography>
      {isDealsLoading && <LinearProgress />}
      <TableContainer
        component={Paper}
        classes={{ root: classes.customTableContainer }}
      >
        <Table
          className={classes.table}
          size="small"
          aria-label="Deals table"
          style={{ borderCollapse: 'separate' }}
        >
          <StickyTableHead>
            <TableRow>
              <StyledTableCell align="center" rowSpan={2}>
                Symbol
              </StyledTableCell>
              <StyledTableCell align="center" colSpan={4}>
                Buy
              </StyledTableCell>
              <StyledTableCell align="center" colSpan={4}>
                Sell
              </StyledTableCell>
              <StyledTableCell align="center" rowSpan={2}>
                Profit
              </StyledTableCell>
              <StyledTableCell align="center" rowSpan={2}>
                Income
              </StyledTableCell>
            </TableRow>

            <TableRow>
              <StyledTableCell align="center">Date</StyledTableCell>
              <StyledTableCell align="center">Quantity</StyledTableCell>
              <StyledTableCell align="center">Price</StyledTableCell>
              <StyledTableCell align="center">
                Cumulative Quantity
              </StyledTableCell>
              <StyledTableCell align="center">Date</StyledTableCell>
              <StyledTableCell align="center">Quantity</StyledTableCell>
              <StyledTableCell align="center">Price</StyledTableCell>
              <StyledTableCell align="center">
                Cumulative Quantity
              </StyledTableCell>
            </TableRow>
          </StickyTableHead>
          <TableBody>
            {!isDealsLoading && deals.length === 0 && (
              <StyledTableRow>
                <StyledTableCell colSpan={11} align="center">
                  Deals list is empty!
                </StyledTableCell>
              </StyledTableRow>
            )}
            {deals.map((deal) => (
              <DealRow deal={deal} getLatestPrice={getLatestPrice} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </BasePage>
  );
};

export default DealsPage;
