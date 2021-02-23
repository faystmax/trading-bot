package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.UserDto;
import com.faystmax.tradingbot.mapper.UserMapper;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/user")
    public UserDto getUserInfo(Principal principal) {
        return userMapper.map(userService.findUserByEmail(principal.getName()));
    }

    @PutMapping("/user")
    public UserDto putUserInfo(UserDto userDto, Principal principal) {
        User updatedUser = userService.updateUser(principal.getName(), userDto);
        return userMapper.map(updatedUser);
    }
}
