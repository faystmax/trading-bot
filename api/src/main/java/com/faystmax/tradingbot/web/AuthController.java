package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.MessageResponse;
import com.faystmax.tradingbot.dto.auth.SignInRequest;
import com.faystmax.tradingbot.dto.auth.SignInResponse;
import com.faystmax.tradingbot.dto.auth.SignUpRequest;
import com.faystmax.tradingbot.exception.SignUpException;
import com.faystmax.tradingbot.service.auth.SignInService;
import com.faystmax.tradingbot.service.auth.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {
    private final SignInService signInService;
    private final SignUpService signUpService;

    @ResponseBody
    @PostMapping("/auth/signIn")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return ok(signInService.signIn(request.getEmail(), request.getPassword()));
    }

    @ResponseBody
    @PostMapping("/auth/signUp")
    public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        try {
            signUpService.signUp(request.getEmail(), request.getPassword());
            return ok(new MessageResponse("User registered successfully!"));
        } catch (SignUpException ex) {
            log.error("SignUp error!", ex);
            return badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
}
