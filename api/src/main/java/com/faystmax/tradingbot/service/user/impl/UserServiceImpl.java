package com.faystmax.tradingbot.service.user.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.dto.UserDto;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepo.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }

    @Nullable
    @Override
    public User findUserByChatId(Long chatId) {
        return userRepo.findByTelegramChatId(chatId).orElse(null);
    }

    @Override
    @Transactional
    public User updateUser(String email, UserDto userDto) {
        User user = findUserByEmail(email);

        user.setTelegramChatId(userDto.getTelegramChatId());
        user.setTradingSymbol(userDto.getTradingSymbol());
        user.setBinanceApiKey(userDto.getBinanceApiKey());
        user.setBinanceSecretKey(userDto.getBinanceSecretKey());
        user.setEmailHost(userDto.getEmailHost());
        user.setEmailPassword(userDto.getEmailPassword());
        user.setEmailFolder(userDto.getEmailFolder());
        return user;
    }
}
