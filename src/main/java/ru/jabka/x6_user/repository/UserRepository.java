package ru.jabka.x6_user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.x6_user.exception.BadRequestException;
import ru.jabka.x6_user.repository.mapper.UserMapper;
import ru.jabka.x6_user.model.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO x6_application.USER (first_name, last_name, surname, email, login, phone, birthday, date_reg, is_active)
            VALUES (:first_name, :last_name, :surname, :email, :login, :phone, :birthday, sysdate, 1)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE x6_application.USER u
            SET u.first_name = :first_name, u.last_name = :last_name, u.surname = :surname,
                u.email = :email, u.login = :login, u.phone = :phone, u.birthday = :birthday
            WHERE u.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM x6_application.USER u
            WHERE u.id = :id and u.is_active = 1;
            """;

    private static final String DELETE = """
            UPDATE x6_application.USER u
            SET u.is_active = 0
            WHERE u.id = :id
            RETURNING *;
            """;

    @Transactional(rollbackFor = Exception.class)
    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public User delete(final User user) {
        return jdbcTemplate.queryForObject(DELETE, userToSql(user), userMapper);
    }

    @Transactional(readOnly = true)
    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
    }

    @Transactional(readOnly = true)
    public Boolean exist(final Long id) {
        try {
            User user = getById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("first_name", user.getFirst_name());
        params.addValue("last_name", user.getLast_name());
        params.addValue("surname", user.getSurname());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("phone", user.getPhone());
        params.addValue("birthday", user.getBirthday());
        params.addValue("date_reg", user.getDate_reg());
        params.addValue("is_active", user.getIs_active());

        return params;
    }
}