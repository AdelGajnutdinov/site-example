package ru.kai.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.kai.models.Product;

import java.util.List;
import java.util.Optional;

public class ProductsDaoHibernateImpl implements ProductsDao {

    //language=HQL
    private final String HQL_SELECT_ALL = "from Product";
    //language=HQL
    private final String HQL_SELECT_BY_ID = "from Product where id = :id";
    //language=HQL
    private final String HQL_SELECT_BY_NAME = "from Product where name = :name";
    //language=HQL
    private final String HQL_SELECT_BY_NAME_AND_PRICE =
            "from Product where name = :name and price = :price";
    //language=HQL
    private final String HQL_UPDATE =
            "update Product set name = :name, price = :price where id = :id";
    //language=HQL
    private final String HQL_DELETE = "delete Product where id = :id";


    private Session session;

    public ProductsDaoHibernateImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        Query query = session.createQuery(HQL_SELECT_BY_NAME);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public boolean isProductExists(Product product) {
        Query query = session.createQuery(HQL_SELECT_BY_NAME_AND_PRICE);
        query.setParameter("name", product.getName());
        query.setParameter("price", product.getPrice());
        return query.getSingleResult() != null;
    }

    @Override
    public Optional<Product> find(Integer id) {
        Query query = session.createQuery(HQL_SELECT_BY_ID);
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public void save(Product model) {
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
    }

    @Override
    public void update(Product model) {
        Query query = session.createQuery(HQL_UPDATE);
        query.setParameter("name", model.getName());
        query.setParameter("price", model.getPrice());
        query.setParameter("id", model.getId());
        query.executeUpdate();
    }

    @Override
    public void delete(Integer id) {
        Query query = session.createQuery(HQL_DELETE);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Product> findAll() {
        return session.createQuery(HQL_SELECT_ALL, Product.class).getResultList();
    }
}
