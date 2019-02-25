package com.yetanotherbank.api.test;

import com.yetanotherbank.api.client.AccountServiceApi;
import com.yetanotherbank.api.client.UserServiceApi;
import org.jooq.generated.public_.tables.pojos.MtAccount;
import org.jooq.generated.public_.tables.pojos.MtUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountApiTest extends ApiClientBasedTest<AccountServiceApi> {

    protected MtUser user;
    protected List<MtAccount> cache = new ArrayList<>();

    public AccountApiTest() {
        super(AccountServiceApi.class);
    }

    @Before
    public void prepareData() throws IOException {
        user = extractBody(clientService(UserServiceApi.class).addUser(
                new MtUser(null, String.join("@", UUID.randomUUID().toString(), "email.com"),
                        "Some", "Another","Guy", "US")));
    }

    @After
    public void cleanData() throws IOException {
        for (MtAccount u : cache) {
            clientService().deleteAccount(u.getId()).execute();
        }
        clientService(UserServiceApi.class).deleteUser(user.getId()).execute();
        cache.clear();
    }

    @Test
    public void createAccountTest() throws IOException {
        MtAccount account = new MtAccount(null, user.getId(), BigDecimal.ZERO, "RUB");


        MtAccount saved = extractBody(clientService().addAccount(account));

        Assert.assertNotNull(saved);
        Assert.assertNotNull(saved.getId());
        assertAccountEquals(account, saved);

        cache.add(saved);
    }

    @Test
    public void searchByIdTest() throws IOException {
        MtAccount account = extractBody(clientService().addAccount(
                new MtAccount(null, user.getId(), BigDecimal.ZERO, "RUB")));

        MtAccount finded = extractBody(clientService().getAccountById(account.getId()));

        Assert.assertNotNull(finded);
        Assert.assertNotNull(finded.getId());
        assertAccountEquals(account, finded);

        cache.add(account);
    }

    @Test
    public void updateAccountTest() throws IOException {
        MtAccount account = extractBody(clientService().addAccount(
                new MtAccount(null, user.getId(), BigDecimal.ZERO, "RUB")));

        account.setBallance(BigDecimal.TEN);

        MtAccount saved = extractBody(clientService().updateAccount(account));

        Assert.assertNotNull(saved);
        Assert.assertEquals(account.getId(), saved.getId());
        assertAccountEquals(account, saved);

        cache.add(saved);
    }

    @Test
    public void deleteUserTest() throws IOException {
        MtAccount account = extractBody(clientService().addAccount(
                new MtAccount(null, user.getId(), BigDecimal.ZERO, "RUB")));

        UUID id = extractBody(clientService().deleteAccount(account.getId()));

        Assert.assertNotNull(id);
        Assert.assertEquals(account.getId(), id);
    }

    protected void assertAccountEquals(MtAccount expected, MtAccount actual) {
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertTrue(expected.getBallance().compareTo(actual.getBallance()) == 0);
        Assert.assertEquals(expected.getCurrencyCode(), actual.getCurrencyCode());
    }
}


