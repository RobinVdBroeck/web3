package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ShopServiceTest {
    private ShopService service;
    private Person person;
    private final String password = "1234";

    @Before
    public void setUp() {
        service = new ShopService();
        person = new Person("sinterklaas", "klaas@klaas.be", password, "Klaas", "Claesens");
    }

    @Test
    public void getPerson_should_return_the_person_if_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getPerson(person.getUserid());

        assertEquals(person, personRetrieved);
    }

    @Test
    public void getPerson_should_return_null_if_person_not_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getPerson("Unknown");

        assertNull(personRetrieved);
    }

    @Test
    public void getUserIfAuthenticated_should_return_the_person_if_registered_and_correct_password() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated(person.getUserid(), password);

        assertEquals(person, personRetrieved);
    }

    @Test
    public void getUserIfAuthenticated_should_return_null_if_person_not_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated("Unknown", password);

        assertNull(personRetrieved);
    }

    @Test
    public void getUserIfAuthenticated_should_return_null_if_person_is_registered_but_incorrect_password() {
        service.addPerson(person);

        Person personRetrieved = service.getUserIfAuthenticated(person.getUserid(), "WrongPassword");

        assertNull(personRetrieved);
    }
}
