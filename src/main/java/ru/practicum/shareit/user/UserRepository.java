package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User createUser(UserDto user);

    User getUser(int id);

    User updateUser(User user);

    void deleteUser(int id);

    List<User> getAllUsers();
}
