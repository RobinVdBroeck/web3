package domain;

import java.util.List;

import db.PersonDatabaseInMemory;

public class ShopService {
    private PersonDatabaseInMemory personDb = new PersonDatabaseInMemory();

    public ShopService() {
    }

    public Person getPerson(String personId) {
        return getPersonDb().get(personId);
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

    private PersonDatabaseInMemory getPersonDb() {
        return personDb;
    }
}
