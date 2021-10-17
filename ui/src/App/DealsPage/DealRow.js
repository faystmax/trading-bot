import React from 'react';
import { moneyFormat, priceFormat } from 'utils/currency';
import percentFormat from 'utils/percent';
import dateFormat from 'utils/dateFormat';
import { StyledTableCell, StyledTableRow } from './styles';

const calcColorByIncome = (income) => {
  if (income > 0) {
    return `rgba(188,253,187,${0.3 + income * 2})`;
  }
  if (income < 0) {
    return `rgba(252,209,209,${0.3 + Math.abs(income) * 2})`;
  }
  if (income === 0) {
    return 'rgba(255,251,232,0.71)';
  }
  return '#ffffff';
};

const calcRowColor = (deal) => {
  if (deal.sellOrders.length === 0) {
    return 'rgb(241,241,241)';
  }
  if (!deal.isFilled && deal.sellOrders.length !== 0) {
    return `rgba(229, 92, 255, 0.26)`;
  }
  return calcColorByIncome(deal.dealIncome);
};

const DealRow = ({ deal, getLatestPrice }) => {
  const { sellOrders } = deal;
  const rowSpan = Math.max(sellOrders.length + (deal.isFilled ? 0 : 1), 1);
  const rowColor = calcRowColor(deal);

  let predictedPrice;
  let predictedCumulativeQty;
  let predictedProfit;
  let predictedIncome;
  let predictedColor = 'unset';

  if (!deal.isFilled) {
    predictedPrice = getLatestPrice(deal.symbol);
    predictedCumulativeQty = predictedPrice * deal.buyOrder.notUsedQty;

    const notSelledCumulativeQty =
      deal.buyOrder.notUsedQty * deal.buyOrder.realPrice;
    predictedProfit = predictedCumulativeQty - notSelledCumulativeQty;
    predictedIncome =
      predictedProfit === 0 ? 0 : predictedProfit / notSelledCumulativeQty;
    predictedColor = calcColorByIncome(predictedIncome);
  }

  return (
    <>
      <StyledTableRow key={deal.buyId} style={{ backgroundColor: rowColor }}>
        <StyledTableCell component="th" scope="row" rowSpan={rowSpan}>
          {deal.symbol}
        </StyledTableCell>
        <StyledTableCell align="right" rowSpan={rowSpan}>
          {dateFormat(deal.buyDate)}
        </StyledTableCell>
        <StyledTableCell align="right" rowSpan={rowSpan}>
          {deal.buyQty}
        </StyledTableCell>
        <StyledTableCell align="right" rowSpan={rowSpan}>
          {priceFormat(deal.buyPrice)}
        </StyledTableCell>
        <StyledTableCell align="right" rowSpan={rowSpan}>
          {moneyFormat(deal.buyCumulativeQty)}
        </StyledTableCell>

        <StyledTableCell align="right">
          {deal.isFilled && dateFormat(sellOrders[0].dateUpdate)}
        </StyledTableCell>
        <StyledTableCell align="right">
          {deal.isFilled && sellOrders[0].usedQty}
        </StyledTableCell>
        <StyledTableCell
          align="right"
          style={{ backgroundColor: predictedColor }}
        >
          {priceFormat(
            deal.isFilled ? sellOrders[0].realPrice : predictedPrice,
          )}
        </StyledTableCell>
        <StyledTableCell
          align="right"
          style={{ backgroundColor: predictedColor }}
        >
          {moneyFormat(
            deal.isFilled
              ? sellOrders[0].cumulativeUsedSellQty
              : predictedCumulativeQty,
          )}
        </StyledTableCell>
        <StyledTableCell
          align="right"
          style={{ backgroundColor: predictedColor }}
        >
          {moneyFormat(
            deal.isFilled ? sellOrders[0].dealProfit : predictedProfit,
          )}
        </StyledTableCell>
        <StyledTableCell
          align="right"
          style={{ backgroundColor: predictedColor }}
        >
          {percentFormat(
            deal.isFilled ? sellOrders[0].dealIncome : predictedIncome,
          )}
        </StyledTableCell>
      </StyledTableRow>
      {sellOrders.slice(deal.isFilled ? 1 : 0).map((sellOrder) => (
        <StyledTableRow
          key={sellOrder.id}
          style={{ backgroundColor: rowColor }}
        >
          <StyledTableCell align="right">
            {dateFormat(sellOrder.dateUpdate)}
          </StyledTableCell>
          <StyledTableCell align="right">{sellOrder.usedQty}</StyledTableCell>
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
};

export default DealRow;
