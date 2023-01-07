package com.faystmax.tradingbot;

import com.faystmax.tradingbot.service.mail.impl.MailIdleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.starter.TelegramBotInitializer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@MockBean(value = {TelegramBotInitializer.class, MailIdleServiceImpl.class})
public class AppTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context, "Application context should be loaded successfully!");
    }
}