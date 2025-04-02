package ru.jabka.x6_user.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabka.x6_user.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .first_name(rs.getString("first_name"))
                .last_name(rs.getString("last_name"))
                .surname(rs.getString("surname"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .phone(rs.getString("phone"))
                .birthday(rs.getObject("birthday", LocalDate.class))
                .date_reg(rs.getObject("gate_reg", LocalDate.class))
                .is_active(rs.getInt("is_active"))
                .build();
    }
}