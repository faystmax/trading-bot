import React, { useEffect, useState } from 'react';
import {
  AppBar,
  IconButton,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';
import MoreVertIcon from '@mui/icons-material/MoreVert';

import { useDispatch, useSelector } from 'react-redux';
import { emptyAuth } from 'components/Auth';
import authApi from 'utils/authApi';
import { moneyFormat } from 'utils/currency';

const drawerWidth = 240;

const Header = ({ drawerOpen, setDrawerOpen }) => {
  const navigate = useNavigate();
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
      sx={{
        zIndex: (theme) => theme.zIndex.drawer + 1,
        transition: (theme) =>
          theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
          }),
        ...(drawerOpen && {
          marginLeft: drawerWidth,
          width: `calc(100% - ${drawerWidth}px)`,
          transition: (theme) =>
            theme.transitions.create(['width', 'margin'], {
              easing: theme.transitions.easing.sharp,
              duration: theme.transitions.duration.enteringScreen,
            }),
        }),
      }}
    >
      <Toolbar>
        <IconButton
          edge="start"
          color="inherit"
          aria-label="open drawer"
          onClick={() => setDrawerOpen(true)}
          sx={{
            marginRight: 36,
            ...(drawerOpen && { display: 'none' }),
          }}
        >
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
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
          <MenuItem onClick={() => navigate('/password')}>
            Change password
          </MenuItem>
          <MenuItem onClick={() => dispatch(emptyAuth())}>Logout</MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>
  );
};
export default Header;
