package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryInMemory  {

    private final HashMap<Long, User> users = new HashMap<>();
    private Long index = 0L;


    public User updateUser(User user) {
        Long id = user.getId();
        checkUserEmailForUpdate(user.getEmail(), id);
        users.put(id, user);
        return user;
    }


    public User createUser(UserDto user) {
        checkUserEmail(user.getEmail());
        User newUser = UserMapper.toUserFromDto(user);
        newUser.setId(setIdForUser());
        users.put(index, newUser);
        return newUser;
    }


    public User getUser(Long id) {
        User user = users.get(id);
        if (user == null) {
            return null;
        }
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }


    public void deleteUser(Long id) {
        users.remove(id);
    }


    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    private void checkUserEmail(String email) {
        for (User u : users.values()) {
            if (u.getEmail().equals(email)) {
                throw new EmailValidationException("Пользователь с таким email уже есть");
            }
        }
    }

    private void checkUserEmailForUpdate(String email, Long id) {
        for (User u : users.values()) {
            if (u.getEmail().equals(email) && !u.getId().equals(id)) {
                throw new EmailValidationException("Нельзя обновить email, пользователь" +
                        " с таким email уже есть");
            }
        }
    }

    private Long setIdForUser() {
        return ++index;
    }
}
