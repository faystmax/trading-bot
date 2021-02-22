import React, { useCallback, useEffect, useState } from 'react';
import {
  AppBar,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
} from '@material-ui/core';
import { ExitToApp as ExitToAppIcon } from '@material-ui/icons';
import BookIcon from '@material-ui/icons/Book';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { withStyles } from '@material-ui/core/styles';
import { useDispatch } from 'react-redux';
import { useAuth } from 'utils/auth';
import { alertActions } from '../../components/Alertbar';
import api from '../../utils/api';
import useStyles from './styles';

function currencyFormat(num) {
  return `$${num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')}`;
}

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: '#b3b8ca',
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

const StyledTableRow = withStyles((theme) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}))(TableRow);

const HomePage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { auth, setAuth } = useAuth();
  const [orders, setOrders] = useState([]);

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

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
        if (error.response.status === 401) {
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
    <div className={classes.root}>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" noWrap className={classes.title}>
            Trading-bot
          </Typography>
          <Typography variant="body1" noWrap>
            {auth.email}
          </Typography>
          <IconButton onClick={logOut} color="inherit">
            <ExitToAppIcon />
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <Toolbar />
        <div className={classes.drawerContainer}>
          <List>
            <ListItem button key="MyOrders" selected>
              <ListItemIcon>
                <BookIcon />
              </ListItemIcon>
              <ListItemText primary="MyOrders" />
            </ListItem>
          </List>
        </div>
      </Drawer>
      <main className={classes.content}>
        <Toolbar />
        <TableContainer component={Paper}>
          <Table
            className={classes.table}
            size="small"
            aria-label="a dense table"
          >
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
      </main>
    </div>
  );
};

export default HomePage;
