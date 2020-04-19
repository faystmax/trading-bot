import React from 'react';
import { render } from '@testing-library/react';
import Auth from './Auth';

test('renders learn react link', () => {
  const { getByText } = render(<Auth />);
  const linkElement = getByText(/Trading Bot/i);
  expect(linkElement).toBeInTheDocument();
});
