package ru.practicum.shareit.user.mapper;

import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUserFromDto(UserDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
