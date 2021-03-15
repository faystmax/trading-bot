import React from 'react';
import { render } from '@testing-library/react';
import SignInPage from './SignInPage';

jest.mock('react-redux', () => ({
  useSelector: jest.fn(),
  useDispatch: () => jest.fn(),
}));

test('renders sign in button', () => {
  const { getByText } = render(<SignInPage />);
  const signInButton = getByText(/Sign In/);
  expect(signInButton).toBeInTheDocument();
});
