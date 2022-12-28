import { API_BASE_URL  } from "../config/api-config";

/**
 * 백엔드 요청 유틸 함수
 * 
 * @param {*} api 요청 uri
 * @param {*} method 요청 method
 * @param {*} request 요청 데이터
 * @returns json 형식의 api 응답 데이터
 */
export function call(api, method, request) {
  let options = {
      headers: new Headers({
        "Content-Type":"application/json",
      }),
      url: API_BASE_URL + api,
      method: method,
  };
  if(request) {
    // GET method
    options.body = JSON.stringify(request);
  }
  return fetch(options.url, options).then((response) => {
    if (response.status === 200) {
      return response.json();
    } else if(response.status === 403) {
      window.location.href = "/login/"; // redirect
    } else {
      Promise.reject(response);
      throw Error(response);
    }
  }).catch((error) => {
    console.log("http error");
    console.log(error);
  });
}

/**
 * 로그인 API 서비스
 * @param {*} userDTO username, password를 담은 객체
 * @returns 인증 토큰을 포함한 서버 응답
 */
export function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO)
    .then((response) => {
      if(response.token) {
        // 토큰이 존재하는 경우 Todo 화면으로 redirect
        window.location.href="/";
      }
    })
}