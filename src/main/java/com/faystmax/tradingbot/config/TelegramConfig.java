package com.faystmax.tradingbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {
    @NotBlank
    private String token;
    @NotBlank
    private String botName;
    @NotNull
    private Proxy proxy;
    @NotNull
    private Long chatId;

    @Data
    public static class Proxy {
        @NotBlank
        private String host;
        @NotNull
        private Integer port;
        @NotBlank
        private String type;
        @Nullable
        private String user;
        @Nullable
        private String password;
    }
}
