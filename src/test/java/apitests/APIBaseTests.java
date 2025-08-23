package apitests;

import config.LaunchConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class APIBaseTests {

    @BeforeAll
    static void beforeAll() {
        LaunchConfig config = ConfigFactory.create(LaunchConfig.class, System.getProperties());

        RestAssured.baseURI = config.getBaseUrl();
        RestAssured.basePath = config.getBasePath();
    }
}
