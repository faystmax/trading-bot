package com.faystmax.tradingbot;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.starter.TelegramBotInitializer;

@MockBean(TelegramBotInitializer.class)
@SpringBootTest(classes = TradingBotApplication.class)
public abstract class BaseTest {
}
