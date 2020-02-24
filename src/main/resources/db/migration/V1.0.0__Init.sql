CREATE TABLE orders
(
    id                    IDENTITY                 NOT NULL PRIMARY KEY,
    exchange_id           VARCHAR(256) UNIQUE,
    date_add              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    price                 DECIMAL                  NOT NULL,
    stop_price            DECIMAL,
    orig_qty              DECIMAL                  NOT NULL,
    executed_qty          DECIMAL                  NOT NULL,
    iceberg_qty           DECIMAL,
    cummulative_quote_qty DECIMAL,
    status                VARCHAR(64)              NOT NULL,
    time_in_force         VARCHAR(64)              NOT NULL,
    type                  VARCHAR(64)              NOT NULL,
    side                  VARCHAR(64)              NOT NULL,
    transact_time         LONG
);

CREATE TABLE trade
(
    id                IDENTITY                 NOT NULL PRIMARY KEY,
    exchange_trade_id VARCHAR(256) UNIQUE,
    date_add          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    order_ref         BIGINT                   NOT NULL,
    price             DECIMAL                  NOT NULL,
    qty               DECIMAL                  NOT NULL,
    quote_qty         DECIMAL                  NOT NULL,
    commission        DECIMAL                  NOT NULL,
    commission_asset  DECIMAL                  NOT NULL,
    symbol            VARCHAR(64)              NOT NULL,
    buyer             BOOLEAN                  NOT NULL,
    maker             BOOLEAN                  NOT NULL,
    best_match        BOOLEAN                  NOT NULL,

    FOREIGN KEY (order_ref) REFERENCES orders (ID)
);