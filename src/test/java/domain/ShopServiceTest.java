package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ShopServiceTest {
    private ShopService service;
    private Person person;
    private String userid;
    private final String password = "1234";

    @Before
    public void setUp() {
        service = new ShopService();
        userid = generateRandomUseridInOrderToRunTestMoreThanOnce("sinterklaas");
        person = new Person(userid, "klaas@klaas.be", password, "Klaas", "Claesens");
    }

    @Test
    public void getPerson_should_return_the_person_if_registered() {
        service.addPerson(person);

        Person personRetrieved = service.getPerson(userid);

        assertNotNull(personRetrieved);
        assertTrue(personRetrieved.isCorrectPassword(password));
        assertEquals(userid, personRetrieved.getUserid());
        assertEquals(person.getEmail(), personRetrieved.getEmail());
        assertEquals(person.getFirstName(), personRetrieved.getFirstName());
        assertEquals(person.getLastName(), personRetrieved.getLastName());
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

        Person personRetrieved = service.getUserIfAuthenticated(userid, password);

        assertNotNull(personRetrieved);
        assertTrue(personRetrieved.isCorrectPassword(password));
        assertEquals(userid, personRetrieved.getUserid());
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

        Person personRetrieved = service.getUserIfAuthenticated(userid, "WrongPassword");

        assertNull(personRetrieved);
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

}
