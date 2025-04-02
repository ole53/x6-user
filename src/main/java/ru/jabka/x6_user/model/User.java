package ru.jabka.x6_user.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Long id;
    private String first_name;
    private String last_name;
    private String surname;
    private String email;
    private String login;
    private String phone;
    private LocalDate birthday;
    private LocalDate date_reg;
    private Integer is_active;
}