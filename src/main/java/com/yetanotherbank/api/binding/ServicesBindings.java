package com.yetanotherbank.api.binding;

import com.yetanotherbank.api.component.DatabaseConnector;
import com.yetanotherbank.api.service.DaoContext;
import dagger.Module;
import dagger.Provides;
import org.jooq.generated.public_.tables.daos.MtUserDao;

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
}
