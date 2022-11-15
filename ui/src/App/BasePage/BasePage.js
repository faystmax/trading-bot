import React, { useState } from 'react';

import Box from '@mui/material/Box';
import Header from './parts/Header';
import Menu from './parts/Menu';

const BasePage = ({ children }) => {
  const [drawerOpen, setDrawerOpen] = useState(false);

  return (
    <Box sx={{ display: 'flex' }}>
      <Header drawerOpen={drawerOpen} setDrawerOpen={setDrawerOpen} />
      <Menu drawerOpen={drawerOpen} setDrawerOpen={setDrawerOpen} />
      <Box
        component="main"
        sx={{ flexGrow: 1, h: '100vh', overflow: 'auto', pt: 9, p: 3 }}
      >
        {children}
      </Box>
    </Box>
  );
};
export default BasePage;
