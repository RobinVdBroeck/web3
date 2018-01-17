package db;

import domain.Person;
import domain.Role;

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
        person.setPassword(result.getString("password"), true); // Set the password as already hashed
        person.setEmail(result.getString("email"));
        person.setFirstName(result.getString("first_name"));
        person.setLastName(result.getString("last_name"));
        Array rulesSQL = result.getArray("roles");
        String[] rolesString = (String[]) rulesSQL.getArray();
        for (String roleString : rolesString) {
            Role role = Role.valueOf(roleString);
            person.addRole(role);
        }
        return person;
    }

    @Override
    public Person get(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE user_id = ?")) {
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
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
        try (
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM person")
        ) {
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
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO person(user_id, email, password, first_name, last_name, roles) VALUES(?,?,?,?,?,?)")) {
            statement.setString(1, person.getUserid());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setString(4, person.getFirstName());
            statement.setString(5, person.getLastName());
            statement.setArray(6, connection.createArrayOf("ROLE_ENUM", person.getRoles().toArray()));
            statement.execute();
        } catch (SQLException e) {
            if (e.getSQLState().contains("23505")) {
                throw new DbException("User already exists");
            }
            throw new DbException(e);
        }
    }

    @Override
    public void update(Person person) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE person SET(user_id, email, password, first_name, last_name, roles) = (?,?,?,?,?,?) WHERE user_id = ?")) {
            statement.setString(1, person.getUserid());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setString(4, person.getFirstName());
            statement.setString(5, person.getLastName());
            statement.setArray(6, connection.createArrayOf("ROLE_ENUM", person.getRoles().toArray()));
            statement.setString(7, person.getUserid());
            statement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }

    }

    @Override
    public void delete(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE user_id = ?")) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
