import React from 'react';
import { Box, Typography } from "@mui/material";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import App from './App';
import Login from './Login';

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright "}
      isohyeon, {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

/**
 * 라우팅 규칙이 작성된 컴포넌트
 * @param {*} props 
 * @returns url에 맞는 route 컴포넌트 렌더링
 */
function AppRouter(props) {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App/>}/>
          <Route path="login" element={<Login/>}/>
        </Routes>
      </BrowserRouter>
      <Box mt={5}>
        <Copyright />
      </Box>
    </div>
  );
}

export default AppRouter;