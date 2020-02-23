CREATE TABLE orders
(
    id                  IDENTITY                 NOT NULL PRIMARY KEY,
    exchange_id         VARCHAR(256),
    date_add            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    price               DECIMAL                  NOT NULL,
    stopPrice           DECIMAL,
    origQty             DECIMAL                  NOT NULL,
    executedQty         DECIMAL                  NOT NULL,
    icebergQty          DECIMAL,
    cummulativeQuoteQty DECIMAL                  NOT NULL,
    status              VARCHAR(64)              NOT NULL,
    timeInForce         VARCHAR(64)              NOT NULL,
    type                VARCHAR(64)              NOT NULL,
    side                VARCHAR(64)              NOT NULL,
    transactTime        LONG                     NOT NULL,
);

CREATE TABLE trade
(
    id              IDENTITY                 NOT NULL PRIMARY KEY,
    exchangeOrderId VARCHAR(256),
    date_add        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    orderRef        BIGINT                   NOT NULL,
    price           DECIMAL                  NOT NULL,
    qty             DECIMAL                  NOT NULL,
    quoteQty        DECIMAL                  NOT NULL,
    commission      DECIMAL                  NOT NULL,
    commissionAsset DECIMAL                  NOT NULL,
    symbol          VARCHAR(64)              NOT NULL,
    buyer           BOOLEAN                  NOT NULL,
    maker           BOOLEAN                  NOT NULL,
    bestMatch       BOOLEAN                  NOT NULL,

    FOREIGN KEY (orderRef) REFERENCES orders (ID)
);

