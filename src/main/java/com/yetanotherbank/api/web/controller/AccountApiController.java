package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.jooq.generated.public_.tables.daos.MtAccountDao;
import org.jooq.generated.public_.tables.pojos.MtAccount;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class AccountApiController implements RouteConfiguration {

    private final JsonWebUtils jsonWebUtils;
    private final DaoContext<MtAccountDao> accountDaoContext;

    @Inject
    public AccountApiController(JsonWebUtils jsonWebUtils,
                                @Dao(MtAccountDao.class) DaoContext<MtAccountDao> accountDaoContext) {
        this.jsonWebUtils = jsonWebUtils;
        this.accountDaoContext = accountDaoContext;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.path("/account", () -> {
            sparkService.get("/:id",
                    jsonWebUtils.response((req, res) ->
                            accountDaoContext.apply(dao ->
                                    new DefaultResponse<>(dao.findById(UUID.fromString(req.params("id")))))));

            sparkService.put("/",
                    jsonWebUtils.response((req, res) ->
                            accountDaoContext.apply(dao -> {
                                MtAccount accountForCreate = jsonWebUtils.extractFromBody(req, MtAccount.class);
                                accountForCreate.setId(UUID.randomUUID());
                                dao.insert(accountForCreate);
                                return new DefaultResponse<>(accountForCreate);
                            })));

            sparkService.post("/",
                    jsonWebUtils.response((req, res) ->
                            accountDaoContext.apply(dao -> {
                                MtAccount accountForUpdate = jsonWebUtils.extractFromBody(req, MtAccount.class);
                                dao.update(accountForUpdate);
                                return new DefaultResponse<>(accountForUpdate);
                            })));

            sparkService.delete("/:id",
                    jsonWebUtils.response((req, res) ->
                            accountDaoContext.apply(dao -> {
                                UUID id = UUID.fromString(req.params("id"));
                                dao.deleteById(id);
                                return new DefaultResponse<>(id);
                            })));
        });

        sparkService.get("/accounts", jsonWebUtils.response((req, res) ->
                accountDaoContext.apply(dao ->
                        new DefaultCollectionResponse<>(dao.findAll()))));
    }
}
