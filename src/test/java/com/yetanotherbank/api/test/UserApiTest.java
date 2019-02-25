package com.yetanotherbank.api.test;

import com.yetanotherbank.api.client.UserServiceApi;
import org.jooq.generated.public_.tables.pojos.MtUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserApiTest extends ApiClientBasedTest<UserServiceApi> {

    protected List<MtUser> cache = new CopyOnWriteArrayList<>();

    public UserApiTest() {
        super(UserServiceApi.class);
    }

    @After
    public void cleanData() throws IOException {
        for (MtUser u : cache) {
            clientService().deleteUser(u.getId()).execute();
        }
        cache.clear();
    }

    @Test
    public void createUserTest() throws IOException {
        MtUser newUser = new MtUser(null, String.join("@", UUID.randomUUID().toString(),
                "email.com"), "Some", "Another", "Guy", "US");

        MtUser saved = extractBody(clientService().addUser(newUser));

        Assert.assertNotNull(saved);
        Assert.assertNotNull(saved.getId());
        assertUserEquals(newUser, saved);

        cache.add(saved);
    }

    @Test
    public void searchByIdTest() throws IOException {
        MtUser user = extractBody(clientService().addUser(
                new MtUser(null, "search-u@email.com", "Some",
                        "Another", "Guy", "US")
        ));

        MtUser finded = extractBody(clientService().getUserById(user.getId()));

        Assert.assertNotNull(finded);
        Assert.assertNotNull(finded.getId());
        assertUserEquals(user, finded);

        cache.add(user);
    }

    @Test
    public void searchByEmailTest() throws IOException {
        cache.add(extractBody(clientService().addUser(
                new MtUser(null, "search1@email.com", "Some",
                        "Another", "Guy", "US")
        )));

        cache.add(extractBody(clientService().addUser(
                new MtUser(null, "search2@email.com", "Some",
                        "Another", "Guy", "US")
        )));

        MtUser finded = extractBody(clientService().getUserByEmail("search2@email.com"));

        Assert.assertNotNull(finded);
        Assert.assertNotNull(finded.getId());
    }


    @Test
    public void updateUserTest() throws IOException {
        MtUser user = extractBody(clientService().addUser(
                new MtUser(null, String.join("@", UUID.randomUUID().toString(), "email.com"),
                        "Some","Another", "Guy", "US")
        ));

        user.setFirstName("Test name");

        MtUser saved = extractBody(clientService().updateUser(user));

        Assert.assertNotNull(saved);
        Assert.assertEquals(user.getId(), saved.getId());
        assertUserEquals(user, saved);

        cache.add(saved);
    }

    @Test
    public void deleteUserTest() throws IOException {
        MtUser user = extractBody(clientService().addUser(
                new MtUser(null, String.join("@", UUID.randomUUID().toString(), "email.com"),
                        "Some","Another", "Guy", "US")
        ));

        UUID id = extractBody(clientService().deleteUser(user.getId()));

        Assert.assertNotNull(id);
        Assert.assertEquals(user.getId(), id);
    }

    protected void assertUserEquals(MtUser expected, MtUser actual) {
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getCountryCode(), actual.getCountryCode());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getMiddleName(), actual.getMiddleName());
    }
}


