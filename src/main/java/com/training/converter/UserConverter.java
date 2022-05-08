package com.training.converter;

import com.training.dto.user.UserDto;
import com.training.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDto, User> {

    @Override
    public UserDto toDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            return userDto;
        } else {
            return null;
        }
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto != null) {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            return user;
        } else {
            return null;
        }
    }
}
