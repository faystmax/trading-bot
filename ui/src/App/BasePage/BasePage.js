import React, { useCallback, useState } from 'react';
import clsx from 'clsx';
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
import PersonIcon from '@material-ui/icons/Person';
import { useHistory } from 'react-router-dom';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import { useAuth } from 'utils/auth';
import useStyles from './styles';

const BasePage = ({ children }) => {
  const classes = useStyles();
  const history = useHistory();
  const { auth, setAuth } = useAuth();
  const [open, setOpen] = useState(false);

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <div className={classes.root}>
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, open && classes.appBarShift)}
      >
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(
              classes.menuButton,
              open && classes.menuButtonHidden,
            )}
          >
            <MenuIcon />
          </IconButton>
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
        variant="permanent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        {' '}
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <div>
          <List>
            <ListItem
              button
              key="My Orders"
              className={classes.menuListItem}
              onClick={() => history.push('/')}
              selected={window.location.pathname === '/'}
            >
              <ListItemIcon>
                <BookIcon />
              </ListItemIcon>
              <ListItemText primary="My Orders" />
            </ListItem>
            <ListItem
              button
              key="Profile"
              className={classes.menuListItem}
              onClick={() => history.push('/profile')}
              selected={window.location.pathname === '/profile'}
            >
              <ListItemIcon>
                <PersonIcon />
              </ListItemIcon>
              <ListItemText primary="Profile" />
            </ListItem>
          </List>
        </div>
      </Drawer>
      <main className={classes.content}>
        <Toolbar />
        {children}
      </main>
    </div>
  );
};
export default BasePage;
