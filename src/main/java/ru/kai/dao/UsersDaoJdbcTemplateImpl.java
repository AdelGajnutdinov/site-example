package ru.kai.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kai.models.User;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

public class UsersDaoJdbcTemplateImpl implements UsersDao {

    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM fix_user";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM fix_user WHERE id = ?";
    //language=SQL
    private final String SQL_SELECT_BY_NAME =
            "SELECT * FROM fix_user " +
                    "WHERE name = ?";
    //language=SQL
    private final String SQL_SELECT_BY_NAME_AND_PASSWORD =
            "SELECT * FROM fix_user " +
                    "WHERE name = ? AND password = ?";
    //language=SQL
    private final String SQL_INSERT = "INSERT INTO fix_user"
            + "(name, password, birth_date) VALUES (?,?,?)";
    //language=SQL
    private final String SQL_UPDATE = "UPDATE fix_user"
            + "SET name = ?, password = ?, birth_date = ? WHERE fix_user.id = ?";
    //language=SQL
    private final String SQL_DELETE = "DELETE FROM fix_user WHERE id = ?";


    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String passwordHash = resultSet.getString("password");
        String birthDate = resultSet.getString("birth_date");
        return new User(id, name, passwordHash, LocalDate.parse(birthDate));
    };

    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        List<User> users = jdbcTemplate.query(SQL_SELECT_BY_NAME, userRowMapper, name);
        if (!users.isEmpty()) {
            return Optional.of(users.get(0));
        }
        return Optional.empty();
    }

    @Override
    public boolean isUserExists(User user) {
        List<User> users = jdbcTemplate.query(
                SQL_SELECT_BY_NAME_AND_PASSWORD,
                userRowMapper,
                user.getName(),
                user.getPasswordHash());
        return !users.isEmpty();
    }

    @Override
    public Optional<User> find(Integer id) {
        List<User> users = jdbcTemplate.query(SQL_SELECT_BY_ID, userRowMapper, id);
        if (!users.isEmpty()) {
            return Optional.of(users.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void save(User model) {
        try {
            jdbcTemplate.update(
                    SQL_INSERT,
                    model.getName(),
                    model.getPasswordHash(),
                    model.getBirthDate().toString());
        } catch (DataAccessException exception) {
            throw new IllegalStateException("This name is already exists!");
        }
    }

    @Override
    public void update(User model) {
        jdbcTemplate.update(
                SQL_UPDATE,
                model.getName(),
                model.getPasswordHash(),
                model.getBirthDate().toString(),
                model.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }
}
