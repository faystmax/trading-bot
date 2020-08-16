package com.faystmax.tradingbot.config.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    @NotBlank
    private String host;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String folder;
}
