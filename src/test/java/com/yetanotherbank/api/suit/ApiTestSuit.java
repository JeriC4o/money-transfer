package com.yetanotherbank.api.suit;

import com.yetanotherbank.api.rule.ApplicationServerRule;
import com.yetanotherbank.api.test.AccountApiTest;
import com.yetanotherbank.api.test.CurrencyApiTest;
import com.yetanotherbank.api.test.TransactionApiTest;
import com.yetanotherbank.api.test.UserApiTest;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CurrencyApiTest.class,
        UserApiTest.class,
        AccountApiTest.class,
        TransactionApiTest.class,
})
public class ApiTestSuit {

    @ClassRule
    public static ApplicationServerRule applicationServerRule = new ApplicationServerRule();
}
