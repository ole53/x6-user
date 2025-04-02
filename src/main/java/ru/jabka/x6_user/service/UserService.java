package ru.jabka.x6_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.x6_user.model.User;
import ru.jabka.x6_user.repository.UserRepository;
import ru.jabka.x6_user.exception.BadRequestException;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user) {
        validate(user);

        return userRepository.insert(user);
    }

    @Cacheable(value = "user", key = "#id")
    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    @Cacheable(value = "user", key = "#id")
    public Boolean exist(final Long id) { return userRepository.exist(id); }

    @CachePut(value = "user", key = "#user.id")
    public User update(final User user) {
        validate(user);

        return userRepository.update(user);
    }

    @CacheEvict(value = "user", key = "#id")
    public User delete(final Long id) {
        final User user = getById(id);
        return userRepository.delete(user);
    }

    private void validate(final User user) {
        if (user == null) {
            throw new BadRequestException("Введите информацию о пользователе");
        }
        if (!StringUtils.hasText(user.getFirst_name())) {
            throw new BadRequestException("Укажите имя!");
        }
        if (!StringUtils.hasText(user.getLast_name())) {
            throw new BadRequestException("Укажите фамилию!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Необходимо указать адрес электронной почты пользователя!");
        }
        if (!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", user.getEmail())) {
            throw new BadRequestException("Введен некорректный email!");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new BadRequestException("Необходимо указать логин!");
        }
        if (!StringUtils.hasText(user.getPhone())) {
            throw new BadRequestException("Необходимо указать номер телефона!");
        }
        if (!Pattern.matches("^((8|[\\+]?(7))[\\-]?)?" + "(\\(?([0-9]{3})\\)?[\\-]?)" + "[[0-9]\\-]{7,10}$",user.getPhone())) {
            throw new BadRequestException("Введен некорректный номер телефона!");
        }
        if (user.getBirthday() == null) {
            throw new BadRequestException("Необходимо указать дату рождения!");
        }
    }
}