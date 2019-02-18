package com.yetanotherbank.api.web.controller;

import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.util.JsonWebUtils;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferApiController implements RouteConfiguration {

    private JsonWebUtils jsonWebUtils;

    @Inject
    public TransferApiController(JsonWebUtils jsonWebUtils) {
        this.jsonWebUtils = jsonWebUtils;
    }

    @Override
    public void configure(Service sparkService) {
        sparkService.get("/", (req, res) -> "test");
    }
}
