import { Grid, TextField, Typography } from '@mui/material';
import { Container } from '@mui/system';
import React from 'react';
import { Button } from 'reactstrap';
import { signin } from './util/ApiService';

const Login = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const username = data.get('username');
    const password = data.get('password');
    // ApiService의 signin 메서드를 사용해 로그인
    signin({username:username, password:password});
  }

  return (
    <Container component='main' maxWidth='xs' style={{marginTop: '8%'}}>
      <Grid container sapcing={2} style={{marginBottom: '3%'}}>
        <Grid item xs={12}>
          <Typography component='h1' variant='h5' style={{textAlign: 'center'}}>
            로그인
          </Typography>
        </Grid>
      </Grid>
      <form noValidate onSubmit={handleSubmit}>
        {" "}
        {/* submit 버튼을 누르면 handleSubmit이 실행됨 */}
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField 
              variant='outlined'
              required
              fullWidth
              id='username'
              label='아이디'
              name='username'
              autoComplete='username'
            />
          </Grid>
          <Grid item xs={12}>
            <TextField 
              type='password'
              variant='outlined'
              required
              fullWidth
              id='password'
              label='패스워드'
              name='password'
              autoComplete='current-password'
            />
          </Grid>
          <Grid item xs={12}>
            <Button type='submit' variant='contained' color='primary' style={{width:'100%'}}>로그인</Button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
};

export default Login;