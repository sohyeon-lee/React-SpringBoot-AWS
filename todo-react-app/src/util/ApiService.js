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
    if(response.status === 200) {
      // let resData = JSON.parse(response.text()); 
      // return resData;
      return response.json();
    }
  }).catch((error) => {
    console.log("http error");
    console.log(error);
  });
}