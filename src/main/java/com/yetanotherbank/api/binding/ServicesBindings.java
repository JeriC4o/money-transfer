package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.core.component.DatabaseConnector;
import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoCallable;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.core.dao.impl.MtCurrencyCourseDaoExtended;
import com.yetanotherbank.api.core.service.TransactionService;
import com.yetanotherbank.api.core.service.TransactionServiceBean;
import dagger.Module;
import dagger.Provides;
import org.jooq.generated.public_.tables.daos.*;

import javax.inject.Singleton;

@Module
public class ServicesBindings {

    @Provides
    @Singleton
    public static TransactionService transferService(TransactionServiceBean transactionServiceBean) {
        return transactionServiceBean;
    }

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

    @Dao(MtCurrencyCourseDao.class)
    @Provides
    public static DaoContext<MtCurrencyCourseDaoExtended> currencyCourseDao(DatabaseConnector connector) {
        return new DaoContext<MtCurrencyCourseDaoExtended>() {
            @Override
            public <R> R apply(DaoCallable<MtCurrencyCourseDaoExtended, R> callable) {
                return connector.transaction(config -> callable.call(new MtCurrencyCourseDaoExtended(config)));
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

    @Dao(MtTransactionLogDao.class)
    @Provides
    public static DaoContext<MtTransactionLogDao> transactionLogDao(DatabaseConnector connector) {
        return new DaoContext<MtTransactionLogDao>() {
            @Override
            public <R> R apply(DaoCallable<MtTransactionLogDao, R> callable) {
                return connector.transaction(config -> callable.call(new MtTransactionLogDao(config)));
            }
        };
    }
}
