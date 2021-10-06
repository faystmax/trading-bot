const dateFormat = (date) => {
  const d = new Date(date);
  const hours = d
    .getHours()
    .toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  const minutes = d
    .getMinutes()
    .toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  const seconds = d
    .getSeconds()
    .toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  const day = d
    .getDate()
    .toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  const month = (d.getMonth() + 1).toLocaleString('en-US', {
    minimumIntegerDigits: 2,
    useGrouping: false,
  });

  return `${day}.${month}.${d.getFullYear()}, ${hours}:${minutes}:${seconds}`;
};

export default dateFormat;
