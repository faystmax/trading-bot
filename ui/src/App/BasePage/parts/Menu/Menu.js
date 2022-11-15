import React from 'react';
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
import Box from '@mui/material/Box';
import mainTheme from '../../../../theme';

const drawerWidth = 240;

const Menu = ({ drawerOpen, setDrawerOpen }) => {
  const navigate = useNavigate();

  return (
    <Drawer
      variant="permanent"
      sx={{
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: (theme) =>
          theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
          }),
        ...(!drawerOpen && {
          overflowX: 'hidden',
          transition: (theme) =>
            theme.transitions.create('width', {
              easing: theme.transitions.easing.sharp,
              duration: theme.transitions.duration.leavingScreen,
            }),
          width: (theme) => theme.spacing(7),
          [mainTheme.breakpoints.up('sm')]: {
            width: mainTheme.spacing(9),
          },
        }),
      }}
      open={drawerOpen}
    >
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'flex-end',
          padding: '0 8px',
          ...mainTheme.mixins.toolbar,
        }}
      >
        <IconButton onClick={() => setDrawerOpen(false)}>
          <ChevronLeftIcon />
        </IconButton>
      </Box>
      <div>
        <List>
          <ListItem
            button
            key="My Deals"
            sx={{
              [mainTheme.breakpoints.up('sm')]: {
                paddingLeft: mainTheme.spacing(3),
              },
            }}
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
            sx={{
              [mainTheme.breakpoints.up('sm')]: {
                paddingLeft: mainTheme.spacing(3),
              },
            }}
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
            sx={{
              [mainTheme.breakpoints.up('sm')]: {
                paddingLeft: mainTheme.spacing(3),
              },
            }}
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
            sx={{
              [mainTheme.breakpoints.up('sm')]: {
                paddingLeft: mainTheme.spacing(3),
              },
            }}
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
