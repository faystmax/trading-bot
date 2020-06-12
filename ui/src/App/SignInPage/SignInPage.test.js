import React from 'react';
import { render } from '@testing-library/react';
import SignInPage from './SignInPage';

test('renders sign in button', () => {
  const { getByText } = render(<SignInPage />);
  const signInButton = getByText(/Sign In/);
  expect(signInButton).toBeInTheDocument();
});
