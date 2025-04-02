package ru.jabka.x6_user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.x6_user.exception.BadRequestException;
import ru.jabka.x6_user.model.User;
import ru.jabka.x6_user.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid(){
        final User user = getUser();
        Mockito.when(userRepository.insert(user)).thenReturn(user);

        User result = userService.create(user);

        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void createUser_withInvalidData_nullEmail_throwsBadRequestException() {
        final User user = getUser();
        user.setEmail(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );

        assertEquals("Необходимо указать адрес электронной почты пользователя!", exception.getMessage());
        verify(userRepository,never()).insert(any());
    }

    @Test
    void createUser_withInvalidData_wrongNumber_throwsBadRequestException() {
        final User user = getUser();
        user.setPhone("vvvasfasf");

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );

        assertEquals("Введен некорректный номер телефона!", exception.getMessage());
        verify(userRepository,never()).insert(any());
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .first_name("UserName")
                .last_name("LastName")
                .surname("SurName")
                .login("UserLogin")
                .birthday(LocalDate.of(1990, 2, 24))
                .email("user@dpd.ru")
                .phone("+79999999")
                .date_reg(LocalDate.of(2025, 4, 2))
                .is_active(1)
                .build();
    }
}