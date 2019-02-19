package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.controller.TransferApiController;
import com.yetanotherbank.api.web.controller.UserApiController;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ApiBindings {

    @Provides
    @IntoSet
    public static RouteConfiguration userApi(UserApiController apiController) {
        return apiController;
    }

    @Provides
    @IntoSet
    public static RouteConfiguration transferApi(TransferApiController apiController) {
        return apiController;
    }
}
