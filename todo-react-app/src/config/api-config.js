let backendHost;
const hostname = window && window.location && window.hostname;

if(hostname === 'localhost') {
    backendHost = 'http://localhost:8080';
}

export const API_BASE_URL = `${backendHost}`;