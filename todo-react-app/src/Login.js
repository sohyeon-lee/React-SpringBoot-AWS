import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React from 'react';
import { Link } from 'react-router-dom';
import { signin, socialLogin } from './util/ApiService';

const Login = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const username = data.get('username');
    const password = data.get('password');
    // ApiService의 signin 메서드를 사용해 로그인
    signin({username:username, password:password});
  }

  const handleSocialLogin = (provider) => {
    socialLogin(provider);
  }

  return (
    <Container component='main' maxWidth='xs' style={{marginTop:'8%'}}>
      <form noValidate onSubmit={handleSubmit}>
        <Grid container spacing={2} style={{marginBottom: '3%'}}>
          <Grid item xs={12}>
            <Typography component='h1' variant='h5' style={{textAlign: 'center'}}>
             로그인
            </Typography>
          </Grid>

          {/* submit 버튼을 누르면 handleSubmit이 실행됨 */}
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
            <Button type='submit' variant='contained' color='primary' fullWidth>로그인</Button>
          </Grid>
          <Grid item xs={12}>
            <Button onClick={() => handleSocialLogin("github")} variant='contained' style={{backgroundColor: '#000'}} fullWidth>깃허브로 로그인하기</Button>
          </Grid>
        </Grid>

        <Grid container justify='flex-end'>
          <Grid item>
            <Link to="/signup" variant='body2'>
              계정이 없습니까? 계정을 생성하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
};

export default Login;