package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.jooq.generated.public_.tables.daos.MtUserDao;
import org.jooq.generated.public_.tables.pojos.MtUser;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserApiController implements RouteConfiguration {

    private JsonWebUtils jsonWebUtils;
    private DaoContext<MtUserDao> userDaoContext;

    @Inject
    public UserApiController(JsonWebUtils jsonWebUtils,
                             @Dao(MtUserDao.class) DaoContext<MtUserDao> userDaoContext) {
        this.jsonWebUtils = jsonWebUtils;
        this.userDaoContext = userDaoContext;
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
