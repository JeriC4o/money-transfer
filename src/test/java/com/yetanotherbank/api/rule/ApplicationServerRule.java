package com.yetanotherbank.api.rule;

import com.yetanotherbank.api.DaggerTestWebApplication;
import com.yetanotherbank.api.TestWebApplication;
import com.yetanotherbank.api.core.component.DatabaseConnector;
import com.yetanotherbank.api.web.WebApplicationServer;
import org.apache.commons.configuration2.Configuration;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.inject.Inject;
import java.io.FileNotFoundException;

public class ApplicationServerRule implements TestRule {

    private static volatile TestWebApplication application;
    private static volatile WebApplicationServer webApplicationServer;

    @Inject
    public DatabaseConnector databaseConnector;

    @Inject
    public Configuration applicationConfiguration;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                start();
                try {
                    base.evaluate(); // This will run the test.
                } finally {
                    webApplicationServer.stop();
                }
            }
        };
    }

    public void start() {
        synchronized (ApplicationServerRule.class) {
            if (application != null) return;
            application = DaggerTestWebApplication.create();
            application.inject(ApplicationServerRule.this);
            webApplicationServer = application.server();
            webApplicationServer.start();

            try {
                databaseConnector.initDatabase(applicationConfiguration.getString("initDb"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
