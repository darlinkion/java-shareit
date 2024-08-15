package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public UserDto createUser(UserDto user) {
        User newUser = UserMapper.toUserFromDto(user);
        return UserMapper.toUserDto(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Нет такого пользователя по id:" + id);
        }
        return UserMapper.toUserDto(user.get());
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto user, Long id) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isEmpty()) {
            throw new NotFoundException("Нет такого пользователя id:" + id
                    + " невозможно сделать обновление");
        }
        if (user.getName() != null) {
            oldUser.get().setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.get().setEmail(user.getEmail());
        }
        User newUser = userRepository.save(oldUser.get());
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}