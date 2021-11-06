package com.faystmax.tradingbot;

import com.faystmax.tradingbot.service.mail.impl.MailIdleServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.starter.TelegramBotInitializer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(classes = {BaseAppTest.TestConfig.class})
public class BaseAppTest {

    @TestConfiguration
    public static class TestConfig {
        @MockBean
        private TelegramBotInitializer telegramBotInitializer;
        @MockBean
        private MailIdleServiceImpl mailIdleService;
    }
}
