package db;

import domain.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDatabasePostgres implements PersonDatabase {
    private final Connection connection;

    public PersonDatabasePostgres(Connection connection) {
        this.connection = connection;
    }

    private Person createPersonFromResultset(ResultSet result) throws SQLException {
        Person person = new Person();
        person.setUserid(result.getString("user_id"));
        person.setPassword(result.getString("password"));
        person.setEmail(result.getString("email"));
        person.setFirstName(result.getString("first_name"));
        person.setLastName(result.getString("last_name"));
        return person;
    }

    @Override
    public Person get(String id) {
        if(id == null) {
            throw new DbException("No id given");
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM person WHERE user_id = " + id);
            if(!result.next()) {
                throw new DbException("No user with user_id " + id + "found");
            }
            return createPersonFromResultset(result);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM person");
            while (result.next()) {
                people.add(createPersonFromResultset(result));
            }
            return people;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void add(Person person) {
        throw new NotImplementedException();
    }

    @Override
    public void update(Person person) {
        throw new NotImplementedException();

    }

    @Override
    public void delete(String id) {
        if(id == null) {
            throw new DbException("No id given");
        }
        throw new NotImplementedException();

    }
}
