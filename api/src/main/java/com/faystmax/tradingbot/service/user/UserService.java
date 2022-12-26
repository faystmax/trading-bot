package com.faystmax.tradingbot.service.user;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    /**
     * Return user by email
     *
     * @param userEmail user email
     * @return user by email
     * @throws UsernameNotFoundException when can't find user
     */
    User findUserByEmail(String userEmail);

    /**
     * Return user by id
     *
     * @param id user id
     * @return user by id
     * @throws UsernameNotFoundException when can't find user
     */
    User findUserById(Long id);

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
     * @param email   user email
     * @param userDto dto
     * @return updated user
     */
    User updateUser(String email, UserDto userDto);

    /**
     * Change password for existing user
     *
     * @param email       user email
     * @param oldPassword old user password
     * @param newPassword new user password
     * @return user with updated password
     */
    User changePassword(String email, String oldPassword, String newPassword);

    /**
     * @param id            id
     * @param activeSymbols activeSymbols
     * @return update user active symbols
     */
    void updateUserActiveSymbols(final Long id, final List<String> activeSymbols);
}
