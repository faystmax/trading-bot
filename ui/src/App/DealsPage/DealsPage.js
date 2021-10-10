import React, { useEffect, useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useDispatch } from 'react-redux';
import { LinearProgress, Typography } from '@material-ui/core';
import BasePage from 'App/BasePage';
import percentFormat from 'utils/percent';
import dateFormat from 'utils/dateFormat';
import { moneyFormat, priceFormat } from 'utils/currency';
import authApi from 'utils/authApi';
import { StyledTableCell, StyledTableRow, useStyles } from './styles';

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

  const calcColorByIncome = (income) => {
    if (income > 0) {
      return `rgba(188,253,187,${income + 0.45})`;
    }
    if (income < 0) {
      return `rgba(252,209,209,${Math.abs(income) + 0.45})`;
    }
    if (income === 0) {
      return 'rgba(255,251,232,0.71)';
    }
    return '#ffffff';
  };

  const calcRowColor = (row) => {
    if (row.sellOrders.length === 0) {
      return 'rgb(241,241,241)';
    }
    if (!row.isFilled && row.sellOrders.length !== 0) {
      return `rgba(229, 92, 255, 0.26)`;
    }
    return calcColorByIncome(row.dealIncome);
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
      <TableContainer component={Paper}>
        <Table
          className={classes.table}
          size="small"
          aria-label="Deals table"
          stickyHeader
        >
          <TableHead>
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
          </TableHead>
          <TableBody>
            {!isDealsLoading && deals.length === 0 && (
              <StyledTableRow>
                <StyledTableCell colSpan={11} align="center">
                  Deals list is empty!
                </StyledTableCell>
              </StyledTableRow>
            )}
            {deals.map((row) => {
              const rowColor = calcRowColor(row);
              const predictedPrice = getLatestPrice(row.symbol);
              const predictedCummulativeQty = predictedPrice * row.buyQty;
              const predictedProfit =
                predictedCummulativeQty - row.buyCumulativeQty;
              const predictedIncome =
                predictedProfit === 0
                  ? 0
                  : predictedProfit / row.buyCumulativeQty;
              const predictedColor = calcColorByIncome(predictedIncome);

              return (
                <>
                  <StyledTableRow
                    key={row.buyId}
                    style={{ backgroundColor: rowColor }}
                  >
                    <StyledTableCell
                      component="th"
                      scope="row"
                      rowSpan={Math.max(row.sellOrders.length, 1)}
                    >
                      {row.symbol}
                    </StyledTableCell>
                    <StyledTableCell
                      align="right"
                      rowSpan={Math.max(row.sellOrders.length, 1)}
                    >
                      {dateFormat(row.buyDate)}
                    </StyledTableCell>
                    <StyledTableCell
                      align="right"
                      rowSpan={Math.max(row.sellOrders.length, 1)}
                    >
                      {row.buyQty}
                    </StyledTableCell>
                    <StyledTableCell
                      align="right"
                      rowSpan={Math.max(row.sellOrders.length, 1)}
                    >
                      {priceFormat(row.buyPrice)}
                    </StyledTableCell>
                    <StyledTableCell
                      align="right"
                      rowSpan={Math.max(row.sellOrders.length, 1)}
                    >
                      {moneyFormat(row.buyCumulativeQty)}
                    </StyledTableCell>

                    {row.sellOrders.length === 0 ? (
                      <>
                        <StyledTableCell align="right" />
                        <StyledTableCell align="right" />
                        <StyledTableCell
                          align="right"
                          style={{ backgroundColor: predictedColor }}
                        >
                          {priceFormat(predictedPrice)}
                        </StyledTableCell>
                        <StyledTableCell
                          align="right"
                          style={{ backgroundColor: predictedColor }}
                        >
                          {moneyFormat(predictedCummulativeQty)}
                        </StyledTableCell>
                        <StyledTableCell
                          align="right"
                          style={{ backgroundColor: predictedColor }}
                        >
                          {moneyFormat(predictedProfit)}
                        </StyledTableCell>
                        <StyledTableCell
                          align="right"
                          style={{ backgroundColor: predictedColor }}
                        >
                          {percentFormat(predictedIncome)}
                        </StyledTableCell>
                      </>
                    ) : (
                      <>
                        <StyledTableCell align="right">
                          {dateFormat(row.sellOrders[0].dateUpdate)}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          {row.sellOrders[0].usedQty}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          {priceFormat(row.sellOrders[0].realPrice)}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          {moneyFormat(row.sellOrders[0].cumulativeUsedSellQty)}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          {moneyFormat(row.sellOrders[0].dealProfit)}
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          {percentFormat(row.sellOrders[0].dealIncome)}
                        </StyledTableCell>
                      </>
                    )}
                  </StyledTableRow>

                  {row.sellOrders.slice(1).map((sellOrder) => (
                    <StyledTableRow
                      key={sellOrder.id}
                      style={{ backgroundColor: rowColor }}
                    >
                      <StyledTableCell align="right">
                        {dateFormat(sellOrder.dateUpdate)}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {sellOrder.usedQty}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {priceFormat(sellOrder.realPrice)}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {moneyFormat(sellOrder.cumulativeQuoteQty)}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {moneyFormat(sellOrder.dealProfit)}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {percentFormat(sellOrder.dealIncome)}
                      </StyledTableCell>
                    </StyledTableRow>
                  ))}
                </>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </BasePage>
  );
};

export default DealsPage;
