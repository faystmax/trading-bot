import React, { useCallback } from 'react';
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
import { useAuth } from 'utils/auth';
import useStyles from './styles';

const BasePage = ({ children }) => {
  const classes = useStyles();
  const history = useHistory();
  const { auth, setAuth } = useAuth();

  const logOut = useCallback(() => {
    setAuth(null);
  }, [setAuth]);

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
            <ListItem
              button
              key="My Orders"
              selected={window.location.pathname === '/'}
            >
              <ListItemIcon>
                <BookIcon />
              </ListItemIcon>
              <ListItemText
                primary="My Orders"
                onClick={() => history.push('/')}
              />
            </ListItem>
            <ListItem
              button
              key="Profile"
              selected={window.location.pathname === '/profile'}
            >
              <ListItemIcon>
                <PersonIcon />
              </ListItemIcon>
              <ListItemText
                primary="Profile"
                onClick={() => history.push('/profile')}
              />
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
