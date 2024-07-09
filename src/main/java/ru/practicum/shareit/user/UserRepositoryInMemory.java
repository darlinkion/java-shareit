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
public class UserRepositoryInMemory implements UserRepository {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int index = 0;

    @Override
    public User updateUser(User user) {
        int id = user.getId();
        checkUserEmailForUpdate(user.getEmail(), id);
        User updatedUser = users.get(id);
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        users.put(id, updatedUser);
        return updatedUser;
    }

    @Override
    public User createUser(UserDto user) {
        checkUserEmail(user.getEmail());
        User newUser = UserMapper.toUserFromDto(user);
        newUser.setId(setIdForUser());
        users.put(index, newUser);
        return newUser;
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }

    @Override
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

    private void checkUserEmailForUpdate(String email, int id) {
        for (User u : users.values()) {
            if (u.getEmail().equals(email) && u.getId() != id) {
                throw new EmailValidationException("Нельзя обновить email, пользователь" +
                        " с таким email уже есть");
            }
        }
    }

    private int setIdForUser() {
        return ++index;
    }
}
