package com.yetanotherbank.api.web;

import spark.Service;

public interface RouteConfiguration {
    void configure(Service sparkService);
}
