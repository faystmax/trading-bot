package com.faystmax.tradingbot.service.auth;

import com.faystmax.tradingbot.dto.JwtDto;

public interface SignInService {
    JwtDto signIn(String email, String password);
}
