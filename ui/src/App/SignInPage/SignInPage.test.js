import React from 'react';
import { render } from '@testing-library/react';
import { AuthContext } from '../../utils/auth';
import SignInPage from './SignInPage';

test('renders sign in button', () => {
  const { getByText } = render(
    <AuthContext.Provider value={{ auth: null }}>
      <SignInPage />
    </AuthContext.Provider>,
  );
  const signInButton = getByText(/Sign In/);
  expect(signInButton).toBeInTheDocument();
});
