package com.yetanotherbank.api;


import com.yetanotherbank.api.binding.ApiBindings;
import com.yetanotherbank.api.binding.CommonBindings;
import com.yetanotherbank.api.binding.ServicesBindings;
import com.yetanotherbank.api.rule.ApplicationServerRule;
import com.yetanotherbank.api.web.WebApplicationServer;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        CommonBindings.class,
        ServicesBindings.class,
        ApiBindings.class,
})
public interface TestWebApplication {
    WebApplicationServer server();
    void inject(ApplicationServerRule context);
}
