import React from 'react';
import { render } from '@testing-library/react';
import SignUpPage from './SignUpPage';

test('renders sign up button', () => {
  const { getByText } = render(<SignUpPage />);
  const signUpButton = getByText(/Sign up new user/);
  expect(signUpButton).toBeInTheDocument();
});
