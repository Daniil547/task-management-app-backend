package io.github.daniil547.user;

import io.github.daniil547.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest extends BaseTest {
    private final DefaultUserService service;

    private static final String USERNAME = "johndoe42";
    private static final String EMAIL = "jd42@example.com";
    private static final String FIRST_NAME = "John";
    private static final String LASTNAME = "Doe";
    private static final String PROFILE_TITLE = FIRST_NAME + " " + LASTNAME + "'s Profile";
    private static User user;

    public static final UUID DUMMY_ID = UUID.fromString("123e4567-e89b-12d3-a456-434414174961");

    public UserTest() {
        service = new DefaultUserService(
                new UserJdbcRepository(dataSource)
        );
    }

    @Test
    @Order(1)
    public void createNew() {

        user = service.create(USERNAME, FIRST_NAME, LASTNAME, EMAIL);

        assertAll(
                () -> assertNotNull(user),
                //username serves 2 purposes:
                () -> assertEquals(USERNAME, user.getUsername()),
                () -> assertEquals(USERNAME, user.getPageName()),

                () -> assertEquals(EMAIL, user.getEmail()),
                () -> assertEquals(FIRST_NAME, user.getFirstName()),
                () -> assertEquals(LASTNAME, user.getLastName()),
                () -> assertEquals(PROFILE_TITLE, user.getPageTitle())
                 );
    }

    @Test
    @Order(2)
    public void createConflicting() {
        assertThrows(IllegalStateException.class, () -> service.create(USERNAME, FIRST_NAME, LASTNAME, EMAIL));
    }

    @Test
    @Order(3)
    public void updateExisting() {
        String newUsername = "gandalf1337";
        String newEmail = "gandalf1337@smth.com";
        String newFirstName = "Gandalf";
        String newLastname = "the White";
        String newProfileTitle = newFirstName + " " + newLastname + "'s Profile";
        String newDescr = "somethingSomethingRing";


        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setFirstName(newFirstName);
        user.setLastName(newLastname);
        user.setPageTitle(newProfileTitle);
        user.setPageDescription(newDescr);

        user = service.update(user);

        assertAll(
                () -> assertEquals(newUsername, user.getUsername()),
                () -> assertEquals(newEmail, user.getEmail()),
                () -> assertEquals(newFirstName, user.getFirstName()),
                () -> assertEquals(newLastname, user.getLastName()),
                () -> assertEquals(newProfileTitle, user.getPageTitle()),
                () -> assertEquals(newDescr, user.getPageDescription())
                 );
    }

    @Test
    @Order(4)
    public void updateNonExisting() {
        UUID actualId = user.getId();
        user.setId(DUMMY_ID);

        assertThrows(IllegalStateException.class, () -> service.update(user));

        user.setId(actualId);
    }

    @Test
    @Order(5)
    public void getExistingById() {
        assertEquals(user, service.getById(user.getId()).get());
    }

    @Test
    @Order(6)
    public void getNonExistingById() {
        assertEquals(Optional.empty(), service.getById(DUMMY_ID));
    }

    @Test
    @Order(7)
    public void getAll() {
        //this is a new one, as we have updated the previous one
        User temp = service.create(USERNAME, FIRST_NAME, LASTNAME, EMAIL);
        List<User> allUsers = service.getAll();
        assertAll(
                () -> assertTrue(allUsers.contains(user)),
                () -> assertTrue(allUsers.contains(temp))
                 );
    }

    @Test
    @Order(8)
    public void deleteById() {
        service.deleteById(user.getId());
        assertEquals(Optional.empty(), service.getById(user.getId()));
    }
}
