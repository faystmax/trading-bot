package com.faystmax.tradingbot.mapper;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    Collection<UserDto> mapAll(Collection<User> users);
}
