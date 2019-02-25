package com.yetanotherbank.api.web.controller;

import com.neovisionaries.i18n.CurrencyCode;
import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.core.dao.impl.MtCurrencyCourseDaoExtended;
import com.yetanotherbank.api.core.model.CurrencyData;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.jooq.Record2;
import org.jooq.generated.public_.tables.daos.MtCurrencyCourseDao;
import org.jooq.generated.public_.tables.pojos.MtCurrencyCourse;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class CurrencyApiController implements RouteConfiguration {

    private JsonWebUtils jsonWebUtils;
    private DaoContext<MtCurrencyCourseDaoExtended> currencyCourseDaoContext;

    @Inject
    public CurrencyApiController(JsonWebUtils jsonWebUtils,
                                 @Dao(MtCurrencyCourseDao.class) DaoContext<MtCurrencyCourseDaoExtended> currencyCourseDaoContext) {
        this.jsonWebUtils = jsonWebUtils;
        this.currencyCourseDaoContext = currencyCourseDaoContext;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.path("/currency", () -> {
            sparkService.get("/:code",
                    jsonWebUtils.response((req, res) ->
                            new DefaultResponse<>(new CurrencyData(
                                    CurrencyCode.getByCode(req.params("code"))))));
        });

        sparkService.path("/currency-course", () -> {
            sparkService.post("/",
                    jsonWebUtils.response((req, res) ->
                            currencyCourseDaoContext.apply(dao -> {
                                MtCurrencyCourse currencyCourseBased = jsonWebUtils.extractFromBody(req, MtCurrencyCourse.class);
                                Record2<String, String> key = dao.compositeKeyRecord(
                                        currencyCourseBased.getFromCurrencyCode(),
                                        currencyCourseBased.getToCurrencyCode());
                                if (dao.existsById(key)) {
                                    dao.update(currencyCourseBased);
                                } else {
                                    dao.insert(currencyCourseBased);
                                }
                                return new DefaultResponse<>(currencyCourseBased);
                            })));

            sparkService.post("/retrieve",
                    jsonWebUtils.response((req, res) ->
                            currencyCourseDaoContext.apply(dao -> {
                                MtCurrencyCourse currencyCourseBased = jsonWebUtils.extractFromBody(req, MtCurrencyCourse.class);
                                Record2<String, String> key = dao.compositeKeyRecord(
                                        currencyCourseBased.getFromCurrencyCode(),
                                        currencyCourseBased.getToCurrencyCode());
                                return new DefaultResponse<>(dao.findById(key));
                            })));

            sparkService.post("/delete",
                    jsonWebUtils.response((req, res) ->
                            currencyCourseDaoContext.apply(dao -> {
                                MtCurrencyCourse currencyCourseBased = jsonWebUtils.extractFromBody(req, MtCurrencyCourse.class);
                                Record2<String, String> key = dao.compositeKeyRecord(
                                        currencyCourseBased.getFromCurrencyCode(),
                                        currencyCourseBased.getToCurrencyCode());
                                dao.deleteById(key);
                                return new DefaultResponse<>(currencyCourseBased);
                            })));

            sparkService.get("/courses", jsonWebUtils.response((req, res) ->
                    currencyCourseDaoContext.apply(dao ->
                            new DefaultCollectionResponse<>(dao.findAll()))));
        });

        sparkService.get("/currencies", jsonWebUtils.response((req, res) ->
                new DefaultCollectionResponse<>(
                        Arrays.stream(CurrencyCode.values())
                                .filter(cc -> Objects.nonNull(cc.getCurrency()))
                .map(CurrencyData::new).collect(Collectors.toList()))));
    }
}
