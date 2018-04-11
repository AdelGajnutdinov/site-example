### Pages
* [Home](http://localhost:8080/home) - Shows usage of cookies, sessions, Filters
* [Login](http://localhost:8080/login) - Shows usage of Pure JDBC, SpringJDBC, BCrypt
* [Products](http://localhost:8080/products) - Shows usage of Hibernate, sessions, Filters
* [SignUp](http://localhost:8080/signUp) - same with login page

### Tables

In this project used only two DB tables: *fix_user* and *fix_product*.

Below you can see SQL queries two create them and insert basic data.
```
CREATE TABLE IF NOT EXISTS fix_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS fix_product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price NUMERIC(8, 2)
);
INSERT INTO fix_product (name, price)
    VALUES ('Apple', 100);
INSERT INTO fix_product (name, price)
    VALUES ('Lemon', 50);
INSERT INTO fix_product (name, price)
    VALUES ('Pineapple', 200);
```
