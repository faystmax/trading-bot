import axios from 'axios';

axios.defaults.headers.Accept = 'application/json';
axios.defaults.headers.ContentType = 'application/json';

export default axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  timeout: 30000,
});
