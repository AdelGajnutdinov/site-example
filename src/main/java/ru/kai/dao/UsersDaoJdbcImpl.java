package ru.kai.dao;

import ru.kai.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbcImpl implements UsersDao {

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

    private Connection connection;

    public UsersDaoJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findUserByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String passwordHash = resultSet.getString("password");
                String birthDate = resultSet.getString("birth_date");
                return Optional.of(new User(id, name, passwordHash, LocalDate.parse(birthDate)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isUserExists(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME_AND_PASSWORD);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPasswordHash());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String passwordHash = resultSet.getString("password");
                String birthDate = resultSet.getString("birth_date");
                return Optional.of(new User(id, name, passwordHash, LocalDate.parse(birthDate)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User model) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPasswordHash());
            preparedStatement.setString(3, model.getBirthDate().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User model) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getPasswordHash());
            preparedStatement.setString(3, model.getBirthDate().toString());
            preparedStatement.setInt(4, model.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String passwordHash = resultSet.getString("password");
                String birthDate = resultSet.getString("birth_date");
                User user = new User(id, name, passwordHash, LocalDate.parse(birthDate));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
