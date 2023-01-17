import Cookies from "universal-cookie";

const cookies = new Cookies();

/**
 * 쿠키 생성
 * @param {String} name 쿠키 key 값
 * @param {String} value 쿠키 value 값
 * @param {Object} option 쿠키 생성 옵션
 * @returns cookies.set(name, value, { ...option })
 */
export const setCookie = (name, value, option) => {
  return cookies.set(name, value, { ...option });
}

/**
 * 쿠키 불러오기
 * @param {String} name 쿠키의 key 값
 * @returns cookies.get(name)
 */
export const getCookie = (name) => {
  return cookies.get(name);
}

/**
 * 쿠키 삭제 
 * @param {String} name 쿠키의 key 값
 * @returns cookies.remove(name)
 */
export const removeCookie = (name) => {
  return cookies.remove(name);
}