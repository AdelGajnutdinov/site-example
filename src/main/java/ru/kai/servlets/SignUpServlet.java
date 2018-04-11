package ru.kai.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kai.crypt.BCryptography;
import ru.kai.dao.UsersDao;
import ru.kai.dao.UsersDaoJdbcImpl;
import ru.kai.dao.UsersDaoJdbcTemplateImpl;
import ru.kai.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String dbDriverClassName = properties.getProperty("db.driverClassName");

            driverManagerDataSource.setUrl(dbUrl);
            driverManagerDataSource.setUsername(dbUsername);
            driverManagerDataSource.setPassword(dbPassword);
            driverManagerDataSource.setDriverClassName(dbDriverClassName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        //usersDao = new UsersDaoJdbcImpl(driverManagerDataSource);
        usersDao = new UsersDaoJdbcTemplateImpl(driverManagerDataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = usersDao.findAll();
        req.setAttribute("usersFromServer", users);
        req.getRequestDispatcher("/jsp/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String passwordHash = BCryptography.hashPassword(req.getParameter("password"));
        LocalDate birthDate = LocalDate.now();
        usersDao.save(new User(name, passwordHash, birthDate));
        doGet(req, resp);
    }
}
