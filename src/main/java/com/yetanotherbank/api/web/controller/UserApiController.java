package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.binding.Dao;
import com.yetanotherbank.api.component.DatabaseConnector;
import com.yetanotherbank.api.service.DaoContext;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.jooq.generated.public_.tables.daos.MtUserDao;
import org.jooq.generated.public_.tables.pojos.MtUser;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class UserApiController implements RouteConfiguration {

    private JsonWebUtils jsonWebUtils;
    private DaoContext<MtUserDao> userDaoContext;
    private DatabaseConnector dc;

    @Inject
    public UserApiController(JsonWebUtils jsonWebUtils,
                             @Dao(MtUserDao.class) DaoContext<MtUserDao> userDaoContext,
                             DatabaseConnector dc) {
        this.jsonWebUtils = jsonWebUtils;
        this.userDaoContext = userDaoContext;
        this.dc = dc;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.path("user", () -> {
            sparkService.get("/:email",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply(userDao ->
                                    userDao.fetchOneByEmail(req.params("email")))));

            sparkService.put("/",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply((MtUserDao userDao) -> {
                                    MtUser userForCreate = jsonWebUtils.extractFromBody(req, MtUser.class);
                                    userDao.insert(userForCreate);
                                    return userForCreate;
                            })));

            sparkService.post("/",
                    jsonWebUtils.response((req, res) ->
                            userDaoContext.apply((MtUserDao userDao) -> {
                                MtUser userForUpdate = jsonWebUtils.extractFromBody(req, MtUser.class);
                                userDao.update(userForUpdate);
                                return userForUpdate;
                            })));
        });
    }
}
