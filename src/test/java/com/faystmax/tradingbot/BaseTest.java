package com.faystmax.tradingbot;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.starter.TelegramBotInitializer;

@RunWith(SpringRunner.class)
@MockBean(TelegramBotInitializer.class)
@SpringBootTest(classes = TradingBotApplication.class)
public abstract class BaseTest {
}
