ALTER TABLE users
    ADD COLUMN telegram_chat_id BIGINT;

ALTER TABLE users
    ADD COLUMN trading_symbol VARCHAR(20);

ALTER TABLE users
    ADD COLUMN binance_api_key VARCHAR(200);

ALTER TABLE users
    ADD COLUMN binance_secret_key VARCHAR(200);