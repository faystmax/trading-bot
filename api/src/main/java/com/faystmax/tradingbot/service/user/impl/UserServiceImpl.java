package com.faystmax.tradingbot.service.user.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepo.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }
}
