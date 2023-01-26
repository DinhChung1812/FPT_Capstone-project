import axios from "../api/axios";

const handleLoginApi = (username, password) => {
    return axios.post('http://localhost:8080/login', { username, password });
}

export { handleLoginApi };