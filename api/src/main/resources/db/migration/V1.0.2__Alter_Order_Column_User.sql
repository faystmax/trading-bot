ALTER TABLE ORDERS
    ADD COLUMN user_ref BIGINT;

ALTER TABLE ORDERS
    ADD FOREIGN KEY (user_ref)
        REFERENCES USER (id);

UPDATE ORDERS
SET ORDERS.user_ref = (select id from USER limit 1);