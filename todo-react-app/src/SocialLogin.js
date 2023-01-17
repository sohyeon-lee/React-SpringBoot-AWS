import React from 'react';
import { Navigate } from 'react-router-dom';
import { setCookie } from './util/Cookie';

const SocialLogin = (props) => {
  const geturlParameter = (name) => { // 쿼리 파라미터에서 값을 추출해주는 함수
    let search = window.location.search;
    let params = new URLSearchParams(search);
    return params.get(name);
  }

  const token = geturlParameter("token");
  console.log('토큰 파싱: ' + token);

  if (token) {
    // 쿠키 토큰 저장
    // setCookie("ACCESS_TOKEN", token, {httpOnly: true}); // httpOnly 설정은 도메인에만 적용할 수 있다.
    setCookie('ACCESS_TOKEN', token);
    
    return (
      <Navigate
        to={{
          pathname: '/',
          state: { from: props.location }, // 페이지 이동 시 파라미터 전달
        }}
      />
    );
  } else {
    return (
      <Navigate
        to={{
          pathname: '/login',
          state: { from: props.location },
        }}
      />
    );
  }
};

export default SocialLogin;