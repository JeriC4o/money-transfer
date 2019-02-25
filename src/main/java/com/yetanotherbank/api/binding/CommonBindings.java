package com.yetanotherbank.api.binding;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.yetanotherbank.api.core.component.ConfigurationLoader;
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
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setDateFormat(new StdDateFormat());
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
