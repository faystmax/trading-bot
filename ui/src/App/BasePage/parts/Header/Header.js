import React, { useEffect, useState } from 'react';
import clsx from 'clsx';
import {
  AppBar,
  IconButton,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from '@material-ui/core';
import { useHistory } from 'react-router-dom';
import MenuIcon from '@material-ui/icons/Menu';
import MoreVertIcon from '@material-ui/icons/MoreVert';

import { useDispatch, useSelector } from 'react-redux';
import { emptyAuth } from 'components/Auth';
import authApi from 'utils/authApi';
import { moneyFormat } from 'utils/currency';
import MailIdle from './MailIdle';
import useStyles from './styles';

const Header = ({ drawerOpen, setDrawerOpen }) => {
  const classes = useStyles();
  const history = useHistory();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const [anchorEl, setAnchorEl] = useState(null);
  const [totalAmount, setTotalAmount] = useState();

  const callTotalAmount = () => {
    authApi.get('binance/total').then((result) => setTotalAmount(result.data));
  };

  useEffect(() => {
    callTotalAmount();
    const interval = setInterval(() => callTotalAmount(), 10 * 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <AppBar
      position="fixed"
      className={clsx(classes.appBar, drawerOpen && classes.appBarShift)}
    >
      <Toolbar>
        <IconButton
          edge="start"
          color="inherit"
          aria-label="open drawer"
          onClick={() => setDrawerOpen(true)}
          className={clsx(
            classes.menuButton,
            drawerOpen && classes.menuButtonHidden,
          )}
        >
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" noWrap className={classes.title}>
          Trading-bot
        </Typography>
        {totalAmount != null && (
          <Typography style={{ paddingRight: 10 }} variant="body1" noWrap>
            {moneyFormat(totalAmount)}
          </Typography>
        )}
        <Typography variant="body1" noWrap>
          {auth.email}
        </Typography>
        <MailIdle />
        <IconButton
          aria-label="menu"
          aria-controls="long-menu"
          aria-haspopup="true"
          onClick={(event) => setAnchorEl(event.currentTarget)}
          color="inherit"
          size="medium"
        >
          <MoreVertIcon />
        </IconButton>
        <Menu
          id="simple-menu"
          anchorEl={anchorEl}
          keepMounted
          open={Boolean(anchorEl)}
          onClose={() => setAnchorEl(null)}
        >
          <MenuItem onClick={() => history.push('/password')}>
            Change password
          </MenuItem>
          <MenuItem onClick={() => dispatch(emptyAuth())}>Logout</MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>
  );
};
export default Header;
