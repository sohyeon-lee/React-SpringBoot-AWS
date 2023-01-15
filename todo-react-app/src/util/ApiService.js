import { API_BASE_URL  } from "../config/api-config";

/**
 * 백엔드 요청 유틸 함수
 * 로그인 시 받은 토큰을 로컬 스토리지에 저장하고, API를 호출할 때마다 로컬 스토리지에서 액세스 토큰을 불러와 헤더에 추가한다.
 * 
 * @param {String} api 요청 uri
 * @param {String} method 요청 method
 * @param {Object} request 요청 데이터
 * @returns json 형식의 api 응답 데이터
 */
export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type":"application/json",
  });

  // 로컬 스토리지에서 access token 가져오기 
  const accessToken  = localStorage.getItem("ACCESS_TOKEN");
  if(accessToken && accessToken !== null) {
    headers.append("Authorization", "Bearer" + accessToken);
  }

  let options = {
      headers: headers,
      url: API_BASE_URL + api,
      method: method,
  };
  if(request) {
    // GET method
    options.body = JSON.stringify(request);
  }
  return fetch(options.url, options)
    .then(response => {
      // json 파싱 전, 상태 코드 확인하여 res값 초기화
      if(response.status === 200) {
        return response.json();
      } else if(response.status === 403) {
        window.location.href = "/login"; // redirect
      } else {
        return response.json();
      }
    })
    .then(response => {
      // res 값에 따른 결과 처리
      if(response.data !== null) {
        return response;
      } else {
        alert(response.error);
      }
    })
}

/**
 * 로그인 API
 * 인증 성공시 로컬 스토리지에 토큰을 저장하고 Todo 리스트 화면으로 redirect 한다.
 * 
 * @param {Object} userDTO username, password를 담은 객체
 * @returns 인증 토큰을 포함한 서버 응답
 */
export function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO)
    .then((response) => {
      if(response) {
        console.log(response.token);
        // 로컬 스토리지에 토큰 저장
        localStorage.setItem("ACCESS_TOKEN", response.token);
        // 토큰이 존재하는 경우 Todo 화면으로 redirect
        window.location.href="/";
      }
    })
}

/**
 * 로그아웃 API
 * 로컬 스토리지에 존재하는 액세스 토큰을 제거하고 로그인 페이지로 redirect한다.
 */
export function signout() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href="/login";
}

/**
 * 계정 생성 API
 * @param {Object} userDTO username, password를 담은 객체
 * @returns 계정 생성 백엔드 요청 메서드
 */
export function signup(userDTO) {
  return call("/auth/signup", "POST", userDTO);
}

/**
 * 소셜 로그인 API
 * 백엔드의 /auth/authorize/{provider}로 브라우저를 redirect한다.
 * @param {String} provider 
 */
export function socialLogin(provider) {
  const frontendUrl = window.location.protocol + "//" + window.location.host;
  window.location.href = API_BASE_URL + "/auth/authorize/" + provider + "?redirect_url=" + frontendUrl;
}