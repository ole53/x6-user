package ru.jabka.x6_user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.x6_user.model.User;
import ru.jabka.x6_user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User get(@PathVariable final Long id) {
        return userService.getById(id);
    }

    @GetMapping("/exist/{id}")
    @Operation(summary = "Проверить существование пользователя по id")
    public Boolean exist(@PathVariable final Long id) {
        return userService.exist(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    public User delete(@PathVariable final Long id) {
        return userService.delete(id);
    }
}