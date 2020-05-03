package com.faystmax.tradingbot.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Not actually a test
 * Just methods for checking binance Api
 */
@Disabled
public class BinanceServiceTest {
    private BinanceApiRestClient restClient;

    @Test
    public void account() {
        Account account = restClient.getAccount();
        assertNotNull(account);
    }
}