package com.faystmax.tradingbot.service.user.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.dto.UserDto;
import com.faystmax.tradingbot.exception.ServiceException;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

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
        user.setEmailUsername(userDto.getEmailUsername());
        user.setEmailPassword(userDto.getEmailPassword());
        user.setEmailFolder(userDto.getEmailFolder());
        return user;
    }

    @Override
    @Transactional
    public User changePassword(String email, String oldPassword, String newPassword) {
        User user = findUserByEmail(email);

        if (encoder.matches(user.getPassword(), oldPassword)) {
            throw new ServiceException("Current password is incorrect!");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new ServiceException("New password is blank!");
        }
        user.setPassword(encoder.encode(newPassword));
        return user;
    }
}
