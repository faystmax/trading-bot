package com.faystmax.tradingbot.config.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.DefaultBotOptions.ProxyType;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Configuration
public class TelegramConfig {
    @Bean
    public DefaultBotOptions botOptions(TelegramProperties properties) {
        DefaultBotOptions options = new DefaultBotOptions();

        // Configuring proxy
        var proxy = properties.getProxy();
        if (proxy != null) {
            configureAuthenticator(proxy.getUser(), proxy.getPassword());
            options.setProxyHost(proxy.getHost());
            options.setProxyPort(proxy.getPort());
            options.setProxyType(ProxyType.valueOf(proxy.getType()));
        }
        return options;
    }

    private void configureAuthenticator(String user, String password) {
        if (isNotBlank(user) && isNotBlank(password))
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password.toCharArray());
                }
            });
    }
}
