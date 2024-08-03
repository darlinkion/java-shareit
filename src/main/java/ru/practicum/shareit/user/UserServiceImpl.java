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
    public UserDto createUser(UserDto user) {
        User tempUser = userRepository.create(user);
        return UserMapper.toUserDto(tempUser);
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.getUser(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Нет такого пользователя по id:" + id);
        }
        return UserMapper.toUserDto(user.get());
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        User oldUser = userRepository.getUser(id);
        if (oldUser == null) {
            throw new NotFoundException("Нет такого пользователя id:" + id
                    + " невозможно сделать обновление");
        }
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        User newUser = userRepository.updateUser(oldUser);
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        getUser(id);
        userRepository.deleteUser(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}