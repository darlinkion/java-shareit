package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto getUser(Long id);

    UserDto updateUser(UserDto user, Long id);

    void deleteUser(Long id);

    List<UserDto> getAllUsers();

}
