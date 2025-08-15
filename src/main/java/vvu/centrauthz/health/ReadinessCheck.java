package vvu.centrauthz.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * Readiness health check to verify if the application is ready to serve requests.
 * Checks database connectivity as part of the readiness check.
 */
@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    private final DataSource dataSource;

    @Inject
    public ReadinessCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public HealthCheckResponse call() {
        try (Connection connection = dataSource.getConnection()) {
            return HealthCheckResponse.builder()
                    .name("Database connection")
                    .up()
                    .withData("database", connection.getMetaData().getURL())
                    .build();
        } catch (SQLException e) {
            return HealthCheckResponse.builder()
                    .name("Database connection")
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
