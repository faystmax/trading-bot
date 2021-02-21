package com.faystmax.tradingbot.service.auth;

public interface SignUpService {
    /**
     * Sign up new user
     *
     * @param email user email
     * @param password user password
     */
    void signUp(String email, String password);
}
