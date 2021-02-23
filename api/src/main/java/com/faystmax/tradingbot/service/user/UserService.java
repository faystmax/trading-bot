package com.faystmax.tradingbot.service.user;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    /**
     * Return user by email
     *
     * @param userEmail user email
     * @return corresponding user with the transferred userEmail
     * @throws UsernameNotFoundException when can't find user
     */
    User findUserByEmail(String userEmail);

    /**
     * Return user by chat id
     *
     * @param chatId chat id
     * @return user or null
     */
    User findUserByChatId(Long chatId);

    /**
     * Updating user fields
     *
     * @param email user email
     * @param userDto dto
     * @return updated user
     */
    User updateUser(String email, UserDto userDto);
}
