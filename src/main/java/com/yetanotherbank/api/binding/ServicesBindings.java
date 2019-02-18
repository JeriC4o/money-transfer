package com.yetanotherbank.api.binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

@Module
public class ServicesBindings {
    @Provides
    public static ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
