import React, { useEffect, useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useDispatch } from 'react-redux';
import { useAuth } from 'hooks/useAuth';
import BasePage from 'App/BasePage';
import { alertActions } from 'components/Alertbar';
import api from 'utils/api';
import currencyFormat from 'utils/currency';
import { StyledTableCell, StyledTableRow, useStyles } from './styles';

const HomePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { auth, logOut } = useAuth();
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      ...(auth && { Authorization: `${auth.type} ${auth.token}` }),
    };

    api({
      method: 'get',
      url: 'orders',
      headers,
    })
      .then((result) => {
        setOrders(result.data);
      })
      .catch((error) => {
        if (!error.response) {
          dispatch(
            alertActions.createAlert({
              message: `Network error!`,
              type: 'error',
            }),
          );
        } else if (error.response.status === 401) {
          logOut();
        } else {
          dispatch(
            alertActions.createAlert({
              message: `Request error! ${error.response.status} ${error.response.data.error}`,
              type: 'error',
            }),
          );
        }
      });
  }, [auth, logOut, dispatch]);

  return (
    <BasePage>
      <TableContainer component={Paper}>
        <Table className={classes.table} size="small" aria-label="Orders table">
          <TableHead>
            <TableRow>
              <StyledTableCell>id</StyledTableCell>
              <StyledTableCell align="right">Price</StyledTableCell>
              <StyledTableCell align="right">OrigQty</StyledTableCell>
              <StyledTableCell align="right">Total value</StyledTableCell>
              <StyledTableCell align="right">Date Add</StyledTableCell>
              <StyledTableCell align="right">Side</StyledTableCell>
              <StyledTableCell align="right">OrderType</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((row) => (
              <StyledTableRow key={row.id}>
                <StyledTableCell component="th" scope="row">
                  {row.id}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {currencyFormat(row.price)}
                </StyledTableCell>
                <StyledTableCell align="right">{row.origQty}</StyledTableCell>
                <StyledTableCell align="right">
                  {currencyFormat(row.price * row.origQty)}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {new Date(row.dateAdd).toUTCString()}
                </StyledTableCell>
                <StyledTableCell align="right">{row.side}</StyledTableCell>
                <StyledTableCell align="right">{row.type}</StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </BasePage>
  );
};

export default HomePage;
