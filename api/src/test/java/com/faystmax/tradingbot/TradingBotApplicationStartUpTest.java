package com.faystmax.tradingbot;

import com.faystmax.tradingbot.service.mail.impl.MailIdleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.starter.TelegramBotInitializer;

@ExtendWith(SpringExtension.class)
@MockBean(TelegramBotInitializer.class)
@MockBean(MailIdleServiceImpl.class)
@SpringBootTest(classes = TradingBotApplication.class)
@ActiveProfiles("test")
public class TradingBotApplicationStartUpTest {
    @Test
    public void checkStartUpSuccess() {
        System.out.println("TradingBotApplication starts successfully!");
    }
}
