ALTER TABLE users
    ADD COLUMN email_host VARCHAR(50);

ALTER TABLE users
    ADD COLUMN email_username VARCHAR(50);

ALTER TABLE users
    ADD COLUMN email_password VARCHAR(50);

ALTER TABLE users
    ADD COLUMN email_folder VARCHAR(50);