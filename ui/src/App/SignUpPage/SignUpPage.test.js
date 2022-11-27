import React from 'react';
import { render } from '@testing-library/react';
import SignUpPage from './SignUpPage';

jest.mock('react-router-dom', () => ({
  useLocation: jest.fn(),
}));

jest.mock('react-redux', () => ({
  useSelector: jest.fn(),
  useDispatch: () => jest.fn(),
}));

test('renders sign up button', () => {
  const { getByText } = render(<SignUpPage />);
  const signUpButton = getByText(/Sign up new user/);
  expect(signUpButton).toBeInTheDocument();
});
