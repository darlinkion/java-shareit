package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(UserDto user) {
        return userRepository.createUser(user);
    }

    @Override
    public User getUser(int id) {
        Optional<User> user = Optional.ofNullable(userRepository.getUser(id));
        if (user.isEmpty()) {
            throw new NotFoundException("Нет такого пользователя по id:" + id);
        }
        return user.get();
    }

    @Override
    public User updateUser(UserDto user, int id) {
        getUser(id);
        User newUser = UserMapper.toUserFromDto(user);
        newUser.setId(id);
        return userRepository.updateUser(newUser);
    }

    @Override
    public void deleteUser(int id) {
        getUser(id);
        userRepository.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
