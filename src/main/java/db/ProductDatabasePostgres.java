package db;

import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabasePostgres implements ProductDatabase {
    private final Connection connection;

    public ProductDatabasePostgres(Connection connection) {
        this.connection = connection;
    }

    private Product createProductFromResultSet(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setId(result.getInt("id"));
        product.setDescription(result.getString("description"));
        product.setName(result.getString("name"));
        product.setPrice(result.getDouble("price"));
        return product;
    }

    @Override
    public Product get(Integer id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new DbException("No product with id " + id + " found");
            }
            return createProductFromResultSet(result);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM product");
            while (result.next()) {
                people.add(createProductFromResultSet(result));
            }
            return people;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    //@Override
    public List<Product> getAllSorted() {
        List<Product> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM product ORDER BY name");
            while (result.next()) {
                people.add(createProductFromResultSet(result));
            }
            return people;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void add(Product product) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO product(name, description, price) VALUES(?,?,?)")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE product SET(id, name, description, price) = (?,?,?,?) WHERE id = ?")) {
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
