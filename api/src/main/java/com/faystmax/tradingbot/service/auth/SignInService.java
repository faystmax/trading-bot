package com.faystmax.tradingbot.service.auth;

import com.faystmax.tradingbot.dto.auth.UserAuthResponse;

public interface SignInService {
    /**
     * Sign in user
     *
     * @param email    user email
     * @param password user password
     * @return SignInResponse
     */
    UserAuthResponse signIn(String email, String password);
}
