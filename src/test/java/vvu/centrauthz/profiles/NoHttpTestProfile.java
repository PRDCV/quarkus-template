package vvu.centrauthz.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class NoHttpTestProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
            "quarkus.http.test-port", "0",
            "quarkus.http.test-ssl-port", "0",
            "quarkus.datasource.enabled", "false",
            "quarkus.hibernate-orm.enabled", "false"
        );
    }
}
