package com.goddrinksjava.prep;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@DataSourceDefinition(name = "java:global/jdbc/prep",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:file:~/IdeaProjects/prep/src/main/resources/H2/prep;AUTO_SERVER=TRUE",
        user = "sa",
        password = "ShootYourGooMyDude",
        properties = {"dynamic-reconfiguration-wait-timeout-in-seconds=15"}
)

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/Login",
                errorPage = "/LoginError",
                useForwardToLogin = false
        )
)

@ApplicationScoped
public class AppConfig {
        public String getValue(String key) throws IOException {
                String result;
                Properties prop = new Properties();
                String propFileName = "config.properties";

                try (
                        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                ) {
                        if (inputStream != null) {
                                prop.load(inputStream);
                        } else {
                                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                        }
                        result = prop.getProperty(key);
                }

                return result;
        }
}
