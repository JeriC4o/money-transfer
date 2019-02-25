package com.yetanotherbank.api.test;

import com.yetanotherbank.api.client.AccountServiceApi;
import com.yetanotherbank.api.client.CurrencyServiceApi;
import com.yetanotherbank.api.client.TransactionServiceApi;
import com.yetanotherbank.api.client.UserServiceApi;
import com.yetanotherbank.api.core.model.TransactionData;
import com.yetanotherbank.api.core.model.TransactionLog;
import com.yetanotherbank.api.web.model.AccountBalanceRequest;
import com.yetanotherbank.api.web.model.TransferRequest;
import org.jooq.generated.public_.tables.pojos.MtAccount;
import org.jooq.generated.public_.tables.pojos.MtCurrencyCourse;
import org.jooq.generated.public_.tables.pojos.MtUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class TransactionApiTest extends ApiClientBasedTest<TransactionServiceApi> {

    protected MtUser user;
    protected MtAccount account;
    protected MtAccount anotherAccount;

    public TransactionApiTest() {
        super(TransactionServiceApi.class);
    }

    @Before
    public void prepareData() throws IOException {
        user = extractBody(clientService(UserServiceApi.class).addUser(
                new MtUser(null, "transactionTest@email.com", "Some", "Another",
                        "Guy", "US")));

        account = extractBody(clientService(AccountServiceApi.class).addAccount(
                new MtAccount(null, user.getId(), BigDecimal.valueOf(1000), "RUB")));

        anotherAccount = extractBody(clientService(AccountServiceApi.class).addAccount(
                new MtAccount(null, user.getId(), BigDecimal.valueOf(1000), "USD")));

        clientService(CurrencyServiceApi.class)
                .updateCurrencyCourse(new MtCurrencyCourse("RUB", "USD", 65.52));
        clientService(CurrencyServiceApi.class)
                .updateCurrencyCourse(new MtCurrencyCourse("USD", "RUB", 0.015));
    }

    @After
    public void cleanData() throws IOException {
        clientService(AccountServiceApi.class).deleteAccount(account.getId()).execute();
        clientService(AccountServiceApi.class).deleteAccount(anotherAccount.getId()).execute();
        clientService(UserServiceApi.class).deleteUser(user.getId()).execute();
    }

    @Test
    public void depositMoneyTest() throws IOException {
        AccountBalanceRequest balanceRequest = new AccountBalanceRequest(account.getId(), BigDecimal.valueOf(1000));
        TransactionData transactionData = extractBody(clientService().depositMoney(balanceRequest));

        Assert.assertNotNull(transactionData);
        Assert.assertNotNull(transactionData.getId());
        Assert.assertNotNull(transactionData.getTransactionLogs());
        Assert.assertEquals(1, transactionData.getTransactionLogs().size());
        Assert.assertEquals(user.getId(), transactionData.getUserId());

        TransactionLog log = transactionData.getTransactionLogs().iterator().next();

        Assert.assertEquals(account.getId(), log.getAccountId());
        Assert.assertEquals(BigDecimal.valueOf(1000), log.getAmount());
    }

    @Test
    public void withdrawMoneyTest() throws IOException {
        AccountBalanceRequest balanceRequest = new AccountBalanceRequest(account.getId(), BigDecimal.valueOf(1000));
        TransactionData transactionData = extractBody(clientService().withdrawMoney(balanceRequest));

        Assert.assertNotNull(transactionData);
        Assert.assertNotNull(transactionData.getId());
        Assert.assertNotNull(transactionData.getTransactionLogs());
        Assert.assertEquals(1, transactionData.getTransactionLogs().size());
        Assert.assertEquals(user.getId(), transactionData.getUserId());

        TransactionLog log = transactionData.getTransactionLogs().iterator().next();

        Assert.assertEquals(account.getId(), log.getAccountId());
        Assert.assertEquals(BigDecimal.valueOf(1000).negate(), log.getAmount());
    }

    @Test
    public void transferMoneyTest() throws IOException {
        TransferRequest transferRequest = new TransferRequest(account.getId(), anotherAccount.getId(), BigDecimal.valueOf(1000));
        TransactionData transactionData = extractBody(clientService().transferMoney(transferRequest));

        Assert.assertNotNull(transactionData);
        Assert.assertNotNull(transactionData.getId());
        Assert.assertNotNull(transactionData.getTransactionLogs());
        Assert.assertEquals(2, transactionData.getTransactionLogs().size());
        Assert.assertEquals(user.getId(), transactionData.getUserId());
    }
}


