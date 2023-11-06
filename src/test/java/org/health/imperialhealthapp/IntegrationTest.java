package org.health.imperialhealthapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
@DirtiesContext
// used to re-initialize applicationContext, because testcontainer will be restarted with different exposed ports
@Testcontainers
@ContextConfiguration(initializers = IntegrationTest.TestcontainersInitializer.class)
@TestPropertySource(properties = "security.enabled=false")
public abstract class IntegrationTest {

    static {
        System.setProperty("ENVIRONMENT", "dev");
    }

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("healthapp")
            .withUsername("root")
            .withPassword("root");

    public static class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            );
        }
    }

    @BeforeAll
    public static void beforeClass() {
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void afterClass() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Assert Status of Postgres Container")
    public void test() {
        assertTrue(postgreSQLContainer.isRunning());
    }

}
