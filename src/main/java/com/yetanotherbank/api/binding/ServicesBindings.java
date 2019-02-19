package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.core.component.DatabaseConnector;
import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoCallable;
import com.yetanotherbank.api.core.dao.DaoContext;
import dagger.Module;
import dagger.Provides;
import org.jooq.generated.public_.tables.daos.*;

@Module
public class ServicesBindings {

    @Dao(MtUserDao.class)
    @Provides
    public static DaoContext<MtUserDao> userDao(DatabaseConnector connector) {
        return new DaoContext<MtUserDao>() {
            @Override
            public <R> R apply(DaoCallable<MtUserDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtUserDao(config)));
            }
        };
    }

    @Dao(MtAccountDao.class)
    @Provides
    public static DaoContext<MtAccountDao> accountDao(DatabaseConnector connector) {
        return new DaoContext<MtAccountDao>() {
            @Override
            public <R> R apply(DaoCallable<MtAccountDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtAccountDao(config)));
            }
        };
    }

    @Dao(MtCurrencyDao.class)
    @Provides
    public static DaoContext<MtCurrencyDao> currencyDao(DatabaseConnector connector) {
        return new DaoContext<MtCurrencyDao>() {
            @Override
            public <R> R apply(DaoCallable<MtCurrencyDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtCurrencyDao(config)));
            }
        };
    }

    @Dao(MtCurrencyCourseDao.class)
    @Provides
    public static DaoContext<MtCurrencyCourseDao> currencyCourseDao(DatabaseConnector connector) {
        return new DaoContext<MtCurrencyCourseDao>() {
            @Override
            public <R> R apply(DaoCallable<MtCurrencyCourseDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtCurrencyCourseDao(config)));
            }
        };
    }

    @Dao(MtTransactionDao.class)
    @Provides
    public static DaoContext<MtTransactionDao> transactionDao(DatabaseConnector connector) {
        return new DaoContext<MtTransactionDao>() {
            @Override
            public <R> R apply(DaoCallable<MtTransactionDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtTransactionDao(config)));
            }
        };
    }
}
