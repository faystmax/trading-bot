package com.faystmax.tradingbot.service.auth;

import com.faystmax.tradingbot.dto.auth.SignInResponse;

public interface SignInService {
    /**
     * Sign in user
     *
     * @param email user email
     * @param password user password
     * @return SignInResponse
     */
    SignInResponse signIn(String email, String password);
}
