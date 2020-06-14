CREATE TABLE user
(
    id       IDENTITY                 NOT NULL PRIMARY KEY,
    date_add TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email    VARCHAR(50)              NOT NULL,
    password VARCHAR(120)             NOT NULL,
    role     VARCHAR(20)              NOT NULL,
    enabled  BOOLEAN                  NOT NULL DEFAULT TRUE,
    CONSTRAINT unique_email_constraint UNIQUE (email)
);