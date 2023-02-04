CREATE USER trading_bot WITH ENCRYPTED PASSWORD 'password';
CREATE DATABASE trading_bot WITH OWNER trading_bot;
GRANT ALL ON DATABASE trading_bot TO trading_bot;