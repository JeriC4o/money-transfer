package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.model.DefaultCollectionResponse;
import com.yetanotherbank.api.web.model.DefaultResponse;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.jooq.generated.public_.tables.daos.MtUserDao;
import org.jooq.generated.public_.tables.pojos.MtUser;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class UserApiController implements RouteConfiguration {

    private final JsonWebUtils jsonWebUtils;
    private final DaoContext<MtUserDao> userDaoContext;

    @Inject
    public UserApiController(JsonWebUtils jsonWebUtils,
                             @Dao(MtUserDao.class) DaoContext<MtUserDao> userDaoContext) {
        this.jsonWebUtils = jsonWebUtils;
        this.userDaoContext = userDaoContext;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.path("/user", () -> {
            sparkService.get("/:id",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(dao ->
                                    new DefaultResponse<>(dao.findById(UUID.fromString(req.params("id")))))));

            sparkService.get("/byEmail/:email",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(dao ->
                                    new DefaultResponse<>(dao.fetchOneByEmail(req.params("email"))))));

            sparkService.put("/",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(dao -> {
                                    MtUser userForCreate = jsonWebUtils.extractFromBody(req, MtUser.class);
                                    userForCreate.setId(UUID.randomUUID());
                                    dao.insert(userForCreate);
                                    return new DefaultResponse<>(userForCreate);
                            })));

            sparkService.post("/",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(dao -> {
                                MtUser userForUpdate = jsonWebUtils.extractFromBody(req, MtUser.class);
                                dao.update(userForUpdate);
                                return new DefaultResponse<>(userForUpdate);
                            })));

            sparkService.delete("/:id",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(userDao -> {
                                UUID id = UUID.fromString(req.params("id"));
                                userDao.deleteById(id);
                                return new DefaultResponse<>(id);
                            })));
        });

        sparkService.get("/users", jsonWebUtils.response((req, res) ->
                userDaoContext.apply(userDao ->
                        new DefaultCollectionResponse<>(userDao.findAll()))));
    }
}
