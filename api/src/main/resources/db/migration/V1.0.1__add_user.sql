CREATE TABLE users
(
    id       BIGSERIAL                NOT NULL PRIMARY KEY,
    date_add TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email    VARCHAR(50) UNIQUE       NOT NULL,
    password VARCHAR(120)             NOT NULL,
    role     VARCHAR(20)              NOT NULL,
    enabled  BOOLEAN                  NOT NULL DEFAULT TRUE
);