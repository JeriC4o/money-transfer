package com.yetanotherbank.api.web;

import com.yetanotherbank.api.web.util.JsonWebUtils;
import org.apache.commons.configuration2.Configuration;
import spark.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class WebApplicationServer {

    private final Configuration configuration;
    private final Set<RouteConfiguration> routeConfigurationSet;
    private final JsonWebUtils jsonWebUtils;
    private volatile Service sparkServer;

    @Inject
    public WebApplicationServer(Configuration configuration,
                                Set<RouteConfiguration> routeConfigurationSet,
                                JsonWebUtils jsonWebUtils) {
        this.configuration = configuration;
        this.routeConfigurationSet = routeConfigurationSet;
        this.jsonWebUtils = jsonWebUtils;
    }

    public void start() {
        synchronized (this) {
            if (sparkServer != null) return;
            sparkServer = Service.ignite();
            sparkServer.port(configuration.getInt("port", 8080));
            sparkServer.before("/", "application/json", jsonWebUtils.filter());
            routeConfigurationSet.forEach(c -> c.configure(sparkServer));
        }
    }

    public void stop() {
        if (sparkServer != null) {
            sparkServer.stop();
        }
    }
}
