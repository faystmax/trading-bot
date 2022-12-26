package com.faystmax.tradingbot.config.telegram;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {
    @NotBlank
    private String token;
    @NotBlank
    private String botName;
    @NotNull
    private Long ownerChatId;
    @Nullable
    private Proxy proxy;

    @Data
    public static class Proxy {
        private String host;
        private Integer port;
        private String type;
        private String user;
        private String password;
    }
}
