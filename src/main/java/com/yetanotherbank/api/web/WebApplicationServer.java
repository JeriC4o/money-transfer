package com.yetanotherbank.api.web;

import com.yetanotherbank.api.web.util.JsonWebUtils;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class WebApplicationServer {

    private Set<RouteConfiguration> routeConfigurationSet;
    private JsonWebUtils jsonWebUtils;

    @Inject
    public WebApplicationServer(Set<RouteConfiguration> routeConfigurationSet,
                                JsonWebUtils jsonWebUtils) {
        this.routeConfigurationSet = routeConfigurationSet;
        this.jsonWebUtils = jsonWebUtils;
    }

    public void start() {
        Service sparkServer = Service.ignite();
        sparkServer.port(8080);
        sparkServer.before("/", "application/json", jsonWebUtils.filter());
        routeConfigurationSet.forEach(c -> c.configure(sparkServer));

    }
}
