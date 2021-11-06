ALTER TABLE orders
    ADD COLUMN user_ref BIGINT;

ALTER TABLE orders
    ADD FOREIGN KEY (user_ref)
        REFERENCES users (id);

UPDATE orders
SET user_ref = (select id from users limit 1);