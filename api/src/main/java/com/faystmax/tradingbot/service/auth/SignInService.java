package com.faystmax.tradingbot.service.auth;

import com.faystmax.tradingbot.dto.auth.SignInResponse;

public interface SignInService {
    SignInResponse signIn(String email, String password);
}
