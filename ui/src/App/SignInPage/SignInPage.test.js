import React from 'react';
import { render } from '@testing-library/react';
import SignInPage from './SignInPage';

test('renders learn react link', () => {
  const { getByText } = render(<SignInPage />);
  const linkElement = getByText(/Trading Bot/i);
  expect(linkElement).toBeInTheDocument();
});
