package com.yetanotherbank.api.component;

import org.apache.commons.configuration2.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionalCallable;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseConnector {

    private static final String DB_URL = "database.url";
    private static final String DB_USER= "database.user";
    private static final String DB_PASS = "database.pass";

    private Configuration configuration;

    @Inject
    public DatabaseConnector(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T transaction(TransactionalCallable<? extends T> callback) {
        try (Connection c = DriverManager.getConnection(
                configuration.getString(DB_URL),
                configuration.getString(DB_USER),
                configuration.getString(DB_PASS))) {
            DSLContext context = DSL.using(c, SQLDialect.H2);
            return context.transactionResult(callback);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
