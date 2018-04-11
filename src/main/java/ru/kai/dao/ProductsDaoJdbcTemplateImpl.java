package ru.kai.dao;

import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kai.models.Product;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductsDaoJdbcTemplateImpl implements ProductsDao {

    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM fix_product";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM fix_product WHERE id = ?";
    //language=SQL
    private final String SQL_SELECT_BY_NAME =
            "SELECT * FROM fix_product " +
                    "WHERE name = ?";
    //language=SQL
    private final String SQL_SELECT_BY_NAME_AND_PRICE =
            "SELECT * FROM fix_product " +
                    "WHERE name = ? AND price = ?";
    //language=SQL
    private final String SQL_INSERT = "INSERT INTO fix_product"
            + "(name, price) VALUES (?,?)";
    //language=SQL
    private final String SQL_UPDATE = "UPDATE fix_product"
            + "SET name = ?, price = ? WHERE fix_product.id = ?";
    //language=SQL
    private final String SQL_DELETE = "DELETE FROM fix_product WHERE id = ?";


    private JdbcTemplate jdbcTemplate;

    private RowMapper<Product> productRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        return new Product(id, name, price);
    };

    public ProductsDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return jdbcTemplate.query(SQL_SELECT_BY_NAME, productRowMapper, name);
    }

    @Override
    public boolean isProductExists(Product product) {
        List<Product> products = jdbcTemplate.query(
                SQL_SELECT_BY_NAME_AND_PRICE,
                productRowMapper,
                product.getName(),
                product.getPrice());
        return !products.isEmpty();
    }

    @Override
    public Optional<Product> find(Integer id) {
        List<Product> products = jdbcTemplate.query(SQL_SELECT_BY_ID, productRowMapper, id);
        if (!products.isEmpty()) {
            return Optional.of(products.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void save(Product model) {
        jdbcTemplate.update(
                SQL_INSERT,
                model.getName(),
                model.getPrice());
    }

    @Override
    public void update(Product model) {
        jdbcTemplate.update(
                SQL_UPDATE,
                model.getName(),
                model.getPrice(),
                model.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, productRowMapper);
    }
}
