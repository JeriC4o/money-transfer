package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.web.RouteConfiguration;
import com.yetanotherbank.api.web.controller.AccountApiController;
import com.yetanotherbank.api.web.controller.CurrencyApiController;
import com.yetanotherbank.api.web.controller.TransactionApiController;
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
    public static RouteConfiguration accountApi(AccountApiController apiController) {
        return apiController;
    }

    @Provides
    @IntoSet
    public static RouteConfiguration currencyApi(CurrencyApiController apiController) {
        return apiController;
    }

    @Provides
    @IntoSet
    public static RouteConfiguration transferApi(TransactionApiController apiController) {
        return apiController;
    }
}
