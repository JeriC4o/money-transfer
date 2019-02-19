package com.yetanotherbank.api.binding;

import org.jooq.impl.DAOImpl;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Dao {
    Class<? extends DAOImpl> value();
}
