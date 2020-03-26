package ru.itis.repositories;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Role;
import ru.itis.models.State;
import ru.itis.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


@Component
public class UserRepositoryJdbcTemplateImpl implements UserRepository {


    //language=SQL
    private final String SQL_INSERT_USER = "INSERT INTO server_user (user_name, email, password, confirm_string, role, state) values (?, ?, ?, ?, ?, ?);";
    //language=SQL
    private final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM server_user WHERE email = ?;";
    //language=SQL
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM server_user WHERE id = ?;";
    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM server_user;";
    //language=SQL
    private final String SQL_DELETE_CONFIRM_STRING = "UPDATE server_user SET confirm_string = '', state = 'CONFIRMED' where confirm_string = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        return User.builder()
                .id(row.getLong("id"))
                .email(row.getString("email"))
                .name(row.getString("user_name"))
                .confirmString(row.getString("confirm_string"))
                .role(Role.valueOf(row.getString("role")))
                .state(State.valueOf(row.getString("state")))
                .hashPassword(row.getString("password"))
                .build();
    };

    @Override
    public Optional<User> find(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, new Object[]{email}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteConfirmString(String string) {
        jdbcTemplate.update(SQL_DELETE_CONFIRM_STRING, string);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT_USER);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getHashPassword());
            statement.setString(4, entity.getConfirmString());
            statement.setString(5, entity.getRole().toString());
            statement.setString(6, entity.getState().toString());

            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> find(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }
}
