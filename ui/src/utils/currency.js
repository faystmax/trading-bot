export const priceFormat = (num) => {
  return num.toLocaleString('ru-RU', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 5,
  });
};

export const moneyFormat = (num) => {
  return num.toLocaleString('ru-RU', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};
