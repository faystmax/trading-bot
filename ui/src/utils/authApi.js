import store from 'store';
import { createAlert } from 'components/Alertbar';
import { emptyAuth } from 'components/Auth';
import authApi from './defaultApi';

authApi.interceptors.request.use((config) => {
  const { auth } = store.getState();
  if (auth) {
    // eslint-disable-next-line no-param-reassign
    config.headers.Authorization = `${auth.type} ${auth.token}`;
  }
  return config;
});

authApi.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (!error.response) {
      store.dispatch(
        createAlert({
          message: `Network error!`,
          type: 'error',
        }),
      );
    } else if (error.response.status === 401) {
      store.dispatch(emptyAuth());
    } else {
      store.dispatch(
        createAlert({
          message: `Request error! ${error.response.status} ${error.response.data.error}`,
          type: 'error',
        }),
      );
    }
    return Promise.reject(error);
  },
);

export default authApi;
