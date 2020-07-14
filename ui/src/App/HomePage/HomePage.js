import React from 'react';
import {
  AppBar,
  Button,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
} from '@material-ui/core';
import { MoveToInbox as InboxIcon, Mail as MailIcon } from '@material-ui/icons';
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
          <Typography variant="h6" noWrap>
            Dashboard
          </Typography>
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
            {['Dashboard', 'Starred', 'Send email', 'Drafts'].map(
              (text, index) => (
                <ListItem button key={text}>
                  <ListItemIcon>
                    {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                  </ListItemIcon>
                  <ListItemText primary={text} />
                </ListItem>
              ),
            )}
          </List>
        </div>
      </Drawer>
      <main className={classes.content}>
        <Toolbar />
        <Button variant="contained" color="primary" onClick={logOut}>
          Log out
        </Button>
      </main>
    </div>
  );
};

export default HomePage;
