import React from 'react';
import clsx from 'clsx';
import {
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@material-ui/core';
import PersonIcon from '@material-ui/icons/Person';
import AssignmentIcon from '@material-ui/icons/Assignment';
import { useHistory } from 'react-router-dom';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import AndroidIcon from '@material-ui/icons/Android';
import ReorderIcon from '@material-ui/icons/Reorder';

import useStyles from './styles';

const Menu = ({ drawerOpen, setDrawerOpen }) => {
  const classes = useStyles();
  const history = useHistory();

  return (
    <Drawer
      variant="permanent"
      classes={{
        paper: clsx(
          classes.drawerPaper,
          !drawerOpen && classes.drawerPaperClose,
        ),
      }}
      open={drawerOpen}
    >
      <div className={classes.toolbarIcon}>
        <IconButton onClick={() => setDrawerOpen(false)}>
          <ChevronLeftIcon />
        </IconButton>
      </div>
      <div>
        <List>
          <ListItem
            button
            key="My Deals"
            className={classes.menuListItem}
            onClick={() => history.push('/')}
            selected={window.location.pathname === '/'}
          >
            <ListItemIcon>
              <AssignmentIcon />
            </ListItemIcon>
            <ListItemText primary="My Deals" />
          </ListItem>
          <ListItem
            button
            key="My Orders"
            className={classes.menuListItem}
            onClick={() => history.push('/orders')}
            selected={window.location.pathname === '/orders'}
          >
            <ListItemIcon>
              <ReorderIcon />
            </ListItemIcon>
            <ListItemText primary="My Orders" />
          </ListItem>
          <ListItem
            button
            key="Bot config"
            className={classes.menuListItem}
            onClick={() => history.push('/bot-config')}
            selected={window.location.pathname === '/bot-config'}
          >
            <ListItemIcon>
              <AndroidIcon />
            </ListItemIcon>
            <ListItemText primary="Bot config" />
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
  );
};
export default Menu;
