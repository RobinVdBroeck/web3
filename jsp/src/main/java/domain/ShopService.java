package domain;

import db.PersonDatabase;
import db.PersonDatabaseInMemory;

import java.util.List;

public class ShopService {
    final private PersonDatabase personDb = new PersonDatabaseInMemory();

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

    private PersonDatabase getPersonDb() {
        return personDb;
    }
}
