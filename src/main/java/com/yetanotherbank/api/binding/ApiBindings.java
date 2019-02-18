package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.controller.TransferApiController;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ApiBindings {

    @Provides
    @IntoSet
    public static RouteConfiguration transferApi(TransferApiController apiController) {
        return apiController;
    }
}
