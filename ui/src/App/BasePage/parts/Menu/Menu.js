import React from 'react';
import clsx from 'clsx';
import {
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import AssignmentIcon from '@mui/icons-material/Assignment';
import { useNavigate } from 'react-router-dom';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import AndroidIcon from '@mui/icons-material/Android';
import ReorderIcon from '@mui/icons-material/Reorder';

import useStyles from './styles';

const Menu = ({ drawerOpen, setDrawerOpen }) => {
  const classes = useStyles();
  const navigate = useNavigate();

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
            onClick={() => navigate('/')}
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
            onClick={() => navigate('/orders')}
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
            onClick={() => navigate('/bot-config')}
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
            onClick={() => navigate('/profile')}
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
