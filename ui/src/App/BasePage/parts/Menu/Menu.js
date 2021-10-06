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
import BookIcon from '@material-ui/icons/Book';
import PersonIcon from '@material-ui/icons/Person';
import AssignmentIcon from '@material-ui/icons/Assignment';
import { useHistory } from 'react-router-dom';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';

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
            key="My Deals"
            className={classes.menuListItem}
            onClick={() => history.push('/deals')}
            selected={window.location.pathname === '/deals'}
          >
            <ListItemIcon>
              <AssignmentIcon />
            </ListItemIcon>
            <ListItemText primary="My Deals" />
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
