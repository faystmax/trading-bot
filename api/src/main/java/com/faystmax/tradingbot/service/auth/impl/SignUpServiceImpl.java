package com.faystmax.tradingbot.service.auth.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.exception.SignUpException;
import com.faystmax.tradingbot.service.auth.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.faystmax.tradingbot.db.enums.Role.USER;
import static java.lang.Boolean.TRUE;
import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void signUp(String email, String password) {
        if (userRepo.existsByEmail(email)) {
            throw new SignUpException(format("Email \"{0}\" is already in use!", email));
        }

        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(USER);
        user.setDateAdd(new Date());
        user.setEnabled(TRUE);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
    }
}
