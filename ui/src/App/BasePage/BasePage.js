import React, { useState } from 'react';

import useStyles from './styles';
import Header from './parts/Header';
import Menu from './parts/Menu';

const BasePage = ({ children }) => {
  const classes = useStyles();
  const [drawerOpen, setDrawerOpen] = useState(false);

  return (
    <div className={classes.root}>
      <Header drawerOpen={drawerOpen} setDrawerOpen={setDrawerOpen} />
      <Menu drawerOpen={drawerOpen} setDrawerOpen={setDrawerOpen} />
      <main className={classes.content}>{children}</main>
    </div>
  );
};
export default BasePage;
