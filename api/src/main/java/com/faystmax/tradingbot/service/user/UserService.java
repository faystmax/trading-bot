package com.faystmax.tradingbot.service.user;

import com.faystmax.tradingbot.db.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    /**
     * @param userEmail user email
     * @return corresponding user with the transferred userEmail
     * @throws UsernameNotFoundException when can't find user in db
     */
    User findUserByEmail(String userEmail);
}
