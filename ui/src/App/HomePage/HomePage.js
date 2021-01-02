import React from 'react';
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
import { useAuth } from 'utils/auth';
import useStyles from './styles';

const HomePage = () => {
  const classes = useStyles();
  const { setAuth } = useAuth();

  function logOut() {
    setAuth(null);
  }

  return (
    <div className={classes.root}>
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" noWrap className={classes.title}>
            Dashboard
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
            <ListItem button key="MyOrders">
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
      </main>
    </div>
  );
};

export default HomePage;
