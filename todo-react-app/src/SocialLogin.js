import React from 'react';
import { Navigate } from 'react-router-dom';

const SocialLogin = (props) => {
  const geturlParameter = (name) => { // 쿼리 파라미터에서 값을 추출해주는 함수
    let search = window.location.search;
    let params = new URLSearchParams(search);
    return params.get(name);
  }

  const token = geturlParameter("token");
  console.log('토큰 파싱: ' + token);

  if (token) {
    console.log('로컬 스토리지에 토큰 저장' + token);
    localStorage.setItem('ACCESS_TOKEN', token);
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