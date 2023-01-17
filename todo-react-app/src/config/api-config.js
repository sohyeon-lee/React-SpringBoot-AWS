let backendHost;
const hostname = window && window.location && window.location.hostname;

if(hostname === 'localhost') {
    backendHost = 'http://localhost:8080';
} else {
    backendHost = 'http://192.168.0.4:8080';
}

export const API_BASE_URL = `${backendHost}`;