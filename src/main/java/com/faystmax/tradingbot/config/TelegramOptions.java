package com.faystmax.tradingbot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.DefaultBotOptions.ProxyType;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TelegramOptions {
    private final TelegramConfig config;

    @Bean
    public DefaultBotOptions botOptions() {
        DefaultBotOptions options = new DefaultBotOptions();

        // Configuring proxy
        if (config.getProxy() != null) {
            configureAuthenticator(config);
            options.setProxyHost(config.getProxy().getHost());
            options.setProxyPort(config.getProxy().getPort());
            options.setProxyType(ProxyType.valueOf(config.getProxy().getType()));
        }
        return options;
    }

    private void configureAuthenticator(TelegramConfig config) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    config.getProxy().getUser(),
                    config.getProxy().getPassword().toCharArray()
                );
            }
        });
    }
}
