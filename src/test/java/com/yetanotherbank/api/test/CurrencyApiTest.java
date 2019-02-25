package com.yetanotherbank.api.test;

import com.yetanotherbank.api.client.CurrencyServiceApi;
import com.yetanotherbank.api.core.model.CurrencyData;
import org.jooq.generated.public_.tables.pojos.MtCurrencyCourse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrencyApiTest extends ApiClientBasedTest<CurrencyServiceApi> {

    protected List<MtCurrencyCourse> cache = new ArrayList<>();

    public CurrencyApiTest() {
        super(CurrencyServiceApi.class);
    }

    @After
    public void cleanData() throws IOException {
        for (MtCurrencyCourse cc : cache) {
            clientService().deleteCurrencyCourse(cc).execute();
        }
        cache.clear();
    }

    @Test
    public void currencyListTest() throws IOException {
        Collection<CurrencyData> currencies = extractCollectionBody(clientService().getAllCurrencies());

        Assert.assertNotNull(currencies);
        Assert.assertTrue(!currencies.isEmpty());
    }

    @Test
    public void searchByIdTest() throws IOException {
        CurrencyData currency = extractBody(clientService()
                .getCurrencyByCode("RUB"));

        Assert.assertNotNull(currency);
        Assert.assertEquals(currency.getCode(), "RUB");
    }

    @Test
    public void updateCurrencyCourseTest() throws IOException {
        MtCurrencyCourse currencyCourse = new MtCurrencyCourse("RUB", "USD", 65.38);
        MtCurrencyCourse saved = extractBody(clientService().updateCurrencyCourse(currencyCourse));

        cache.add(saved);

        Assert.assertNotNull(saved);
        assertCurrencyCourseEquals(currencyCourse, saved);
    }


    @Test
    public void deleteCurrencyCourseTest() throws IOException {
        MtCurrencyCourse currencyCourse = new MtCurrencyCourse("RUB", "USD", 65.38);
        MtCurrencyCourse saved = extractBody(clientService().updateCurrencyCourse(currencyCourse));

        MtCurrencyCourse deleted = extractBody(clientService().deleteCurrencyCourse(currencyCourse));

        Assert.assertNotNull(saved);
        assertCurrencyCourseEquals(deleted, saved);
    }

    protected void assertCurrencyCourseEquals(MtCurrencyCourse expected, MtCurrencyCourse actual) {
        Assert.assertEquals(expected.getFromCurrencyCode(), actual.getFromCurrencyCode());
        Assert.assertEquals(expected.getToCurrencyCode(), actual.getToCurrencyCode());
        Assert.assertEquals(expected.getCorrection(), actual.getCorrection());
    }
}


