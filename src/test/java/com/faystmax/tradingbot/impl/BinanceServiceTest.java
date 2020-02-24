package com.faystmax.tradingbot.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Not actually a test
 * Just methods for checking binance Api
 */
@Ignore
public class BinanceServiceTest {
    private BinanceApiRestClient restClient;

    @Test
    public void account() {
        Account account = restClient.getAccount();
        assertNotNull(account);
    }
}