import React, {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
} from 'react';

const authContext = createContext({});

export const useAuth = () => {
  return useContext(authContext);
};

function useProvideAuth() {
  const existingAuth = JSON.parse(localStorage.getItem('auth'));
  const [auth, setAuth] = useState(existingAuth);

  const setAuthInfo = useCallback((data) => {
    localStorage.setItem('auth', JSON.stringify(data));
    setAuth(data);
  }, []);

  return useMemo(() => ({ auth, setAuth: setAuthInfo }), [auth, setAuthInfo]);
}

export function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}
