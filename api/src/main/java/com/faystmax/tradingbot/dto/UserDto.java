package com.faystmax.tradingbot.dto;

import com.faystmax.tradingbot.db.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Date dateAdd;
    private String email;
    private Role role;
    private Boolean enabled;
    private Long telegramChatId;
    private String tradingSymbol;
    private String binanceApiKey;
    private String binanceSecretKey;
    private String emailHost;
    private String emailUsername;
    private String emailPassword;
    private String emailFolder;
}
