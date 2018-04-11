package ru.kai.dao;

import ru.kai.models.Product;

import java.util.List;

public interface ProductsDao extends CrudDao<Product> {
    List<Product> findProductsByName(String name);
    boolean isProductExists(Product product);
}
