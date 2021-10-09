export const priceFormat = (num) => {
  return num.toLocaleString('ru-RU', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: Math.abs(num) > 30 ? 2 : 10,
  });
};

export const moneyFormat = (num) => {
  return num.toLocaleString('ru-RU', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: Math.abs(num) > 1 ? 2 : 10,
  });
};
