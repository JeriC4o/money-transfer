package com.yetanotherbank.api.binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetanotherbank.api.component.ConfigurationLoader;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.inject.Singleton;

@Module
public class CommonBindings {

    @Provides
    @Singleton
    public static ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public static Configuration configuration(ConfigurationLoader configurationLoader) {
        try {
            return configurationLoader.loadConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
