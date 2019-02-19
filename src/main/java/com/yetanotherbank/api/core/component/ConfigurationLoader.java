package com.yetanotherbank.api.core.component;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class ConfigurationLoader {

    public static final String APP_CONFIG = "application.properties";

    @Inject
    public ConfigurationLoader() {
    }

    public Configuration loadConfiguration() throws ConfigurationException {
        File configFile = new File(getClass().getClassLoader().getResource(APP_CONFIG).getFile());
        Configurations configs = new Configurations();
        return configs.properties(configFile);
    }
}
