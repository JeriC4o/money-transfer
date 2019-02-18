package com.yetanotherbank.api;

import com.yetanotherbank.api.binding.ApiBindings;
import com.yetanotherbank.api.binding.ServicesBindings;
import com.yetanotherbank.api.web.WebApplicationServer;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ServicesBindings.class,
        ApiBindings.class,
})
public interface WebApplication {
    WebApplicationServer server();
}
