package ru.kai.servlets;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kai.dao.ProductsDao;
import ru.kai.dao.ProductsDaoHibernateImpl;
import ru.kai.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductsDao productsDao;

    @Override
    public void init() throws ServletException {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/db.properties")));
            properties.getProperty("hibernate.connection.url");
            properties.getProperty("hibernate.connection.username");
            properties.getProperty("hibernate.connection.password");
            properties.getProperty("hibernate.connection.driver_class");
            properties.getProperty("hibernate.dialect");
            properties.getProperty("hibernate.hbm2ddl.auto");
            properties.getProperty("hibernate.show_sql");
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Product.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        productsDao = new ProductsDaoHibernateImpl(session);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productsDao.findAll();
        req.setAttribute("productsFromServer", products);
        req.getRequestDispatcher("/jsp/product.jsp").forward(req, resp);
    }

}
