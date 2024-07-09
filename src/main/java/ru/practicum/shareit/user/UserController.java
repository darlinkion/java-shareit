package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody UserDto user) {
        User tempUser = userService.createUser(user);
        log.info("User created: " + tempUser);
        return tempUser;
    }

    @GetMapping
    public List<User> getAll() {
        List<User> userList = userService.getAllUsers();
        log.info("Get all users: " + userList);
        return userList;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable @Positive int id) {
        User user = userService.getUser(id);
        log.info("Get user: " + user);
        return user;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable @Positive int id) {
        userService.deleteUser(id);
        log.info("Delete user by id: " + id);
    }

    @PatchMapping("{id}")
    public User update(@PathVariable @Positive int id, @RequestBody UserDto user) {
        User tempUser = userService.updateUser(user, id);
        log.info("User updated: " + tempUser);
        return tempUser;
    }
}
