package domain;

import db.*;

import java.sql.Connection;
import java.util.List;

public class ShopService {
    final private PersonDatabase personDb;
    final private ProductDatabase productDb;

    public ShopService() {
        personDb = new PersonDatabaseInMemory();
        productDb = new ProductDatabaseInMemory();
    }

    public ShopService(Connection connection) {
        personDb = new PersonDatabasePostgres(connection);
        productDb = new ProductDatabasePostgres(connection);
    }

    public Person getPerson(String personId) {
        return getPersonDb().get(personId);
    }

    public Person getUserIfAuthenticated(String personId, String password) {
        Person person = getPerson(personId);

        if (person != null && person.isCorrectPassword(password)) {
            return person;
        }
        return null;
    }

    public List<Person> getPersons() {
        return getPersonDb().getAll();
    }

    public void addPerson(Person person) {
        getPersonDb().add(person);
    }

    public void updatePersons(Person person) {
        getPersonDb().update(person);
    }

    public void deletePerson(String id) {
        getPersonDb().delete(id);
    }

    public Product getProduct(int id) {
        return getProductDb().get(id);
    }

    public List<Product> getProducts() {
        return getProductDb().getAll();
    }

    public void addProduct(Product product) {
        getProductDb().add(product);
    }

    public void updateProduct(Product product) {
        getProductDb().update(product);
    }

    public void deleteProduct(int id) {
        getProductDb().delete(id);
    }

    private PersonDatabase getPersonDb() {
        return personDb;
    }

    private ProductDatabase getProductDb() {
        return productDb;
    }
}
