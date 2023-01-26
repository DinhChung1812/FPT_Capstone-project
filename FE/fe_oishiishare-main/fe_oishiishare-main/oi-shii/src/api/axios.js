import axios from 'axios';
const BASE_URL = 'http://localhost:8080';


const instance = axios.create({
    baseURL: process.env.REACT_APP_BACKEND_URL,
});

export const axiosPrivate = axios.create({
    baseURL: BASE_URL,
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
});

instance.interceptors.response.use(
    (response) => {
        const { data } = response;
        return response.data;
    })


export default instance;
