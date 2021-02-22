package com.faystmax.tradingbot.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthResponse {
    private final String type = "Bearer";
    private String token;
    private Long id;
    private String email;
    private List<String> roles;
}
