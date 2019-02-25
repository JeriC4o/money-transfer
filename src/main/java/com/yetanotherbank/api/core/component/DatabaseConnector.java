package com.yetanotherbank.api.core.component;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.dbutils.DbUtils;
import org.jooq.DSLContext;
import org.jooq.TransactionalCallable;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@Singleton
public class DatabaseConnector {

    private static final String DB_URL = "database.url";
    private static final String DB_USER= "database.user";
    private static final String DB_PASS = "database.pass";

    private ConnectionSessionHolder sessionHolder;

    @Inject
    public DatabaseConnector(Configuration configuration) {
        this.sessionHolder = new ConnectionSessionHolder(configuration);
    }

    public <T> T transaction(TransactionalCallable<? extends T> callback) {
        return sessionHolder.connection(callback);
    }

    public void initDatabase(String file) throws FileNotFoundException {
        File configFile = new File(getClass().getClassLoader().getResource(file).getFile());
        FileReader reader = new FileReader(configFile);
        sessionHolder.connectionRaw(c -> {
            Scanner s = new Scanner(reader);
            s.useDelimiter("(;(\r)?\n)|(--\n)");
            Statement st = c.createStatement();
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
            return null;
        });
    }

    public static class ConnectionSessionHolder {
        public static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<>();
        public final Configuration configuration;

        public ConnectionSessionHolder(Configuration configuration) {
            this.configuration = configuration;
        }

        public <T> T connectionRaw(ConnectionFunction<T> connectionFunction) {
            boolean newConnection = CONNECTION.get() == null;
            try {
                if (newConnection) {
                    //new
                    CONNECTION.set(DriverManager.getConnection(
                            configuration.getString(DB_URL),
                            configuration.getString(DB_USER),
                            configuration.getString(DB_PASS)));
                    return connectionFunction.apply(CONNECTION.get());
                } else {
                    //internal call
                    return connectionFunction.apply(CONNECTION.get());
                }
            } catch (SQLException e) {
                DbUtils.rollbackAndCloseQuietly(CONNECTION.get());
                CONNECTION.remove();
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                if (newConnection) {
                    DbUtils.closeQuietly(CONNECTION.get());
                    CONNECTION.remove();
                }
            }

        }

        public <T> T connection(TransactionalCallable<? extends T> callback) {
            return connectionRaw(c -> {
                DSLContext context = DSL.using(c, JDBCUtils.dialect(c));
                return context.transactionResult(callback);
            });
        }
    }

    @FunctionalInterface
    public interface ConnectionFunction<R> {
        R apply(Connection connection) throws SQLException;
    }
}
