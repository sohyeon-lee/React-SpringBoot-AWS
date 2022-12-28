import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React from 'react';
import { Link } from 'react-router-dom';
import { signup } from './util/ApiService';

function SignUp(props) {
  const handleSubmit = (e) => {
    e.preventDefault();
    // FormData 객체에서 form에 저장된 데이터를 맵의 형태로 바꿔줌
    const data = new FormData(e.target);
    const username = data.get('username');
    const password = data.get('password');
    signup({username:username, password:password}).then(
      (response) => {
        // 계정 생성 성공 시 login 페이지로 redirect
        window.location.href='/login';
      }
    )
  }

  return (
    <Container component='main' maxWidth='xs' style={{marginTop:'8%'}}>
      
      <form noValidate onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Typography component='h1' variant='h5'>
              계정 생성
            </Typography>
          </Grid>
          
          <Grid item xs={12}>
            <TextField
              autoComplete='fname'
              name='username'
              variant='outlined'
              required
              fullWidth
              id='username'
              label='아이디'
              autoFocus
              />
          </Grid>
          
          <Grid item xs={12}>
          <TextField
              autoComplete='current-password'
              name='password'
              variant='outlined'
              required
              fullWidth
              id='password'
              label='패스워드'
              type='password'
              />
          </Grid>
          
          <Grid item xs={12}>
            <Button type='submit' fullWidth variant='contained' color='primary'>계정 생성</Button>
          </Grid>
        </Grid>

        <Grid container justify='flex-end'>
          <Grid item>
            <Link to='/login' variant='body2'>
              이미 계정이 있습니까? 로그인하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default SignUp;