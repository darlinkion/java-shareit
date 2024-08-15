package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.marker.Create;
import ru.practicum.shareit.marker.Update;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto create(@Validated(Create.class) @RequestBody UserDto user) {
        UserDto tempUser = userService.createUser(user);
        log.info("User created: " + tempUser);
        return tempUser;
    }

    @GetMapping
    public List<UserDto> getAll() {
        List<UserDto> userList = userService.getAllUsers();
        log.info("Get all users: " + userList);
        return userList;
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable @Positive Long id) {
        UserDto user = userService.getUser(id);
        log.info("Get user: " + user);
        return user;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable @Positive Long id) {
        userService.deleteUser(id);
        log.info("Delete user by id: " + id);
    }

    @PatchMapping("{id}")
    public UserDto update(@PathVariable @Positive Long id, @RequestBody @Validated(Update.class) UserDto user) {
        UserDto tempUser = userService.updateUser(user, id);
        log.info("User updated: " + tempUser);
        return tempUser;
    }
}
