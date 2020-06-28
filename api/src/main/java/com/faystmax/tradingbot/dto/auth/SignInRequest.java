package com.faystmax.tradingbot.dto.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SignInRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
