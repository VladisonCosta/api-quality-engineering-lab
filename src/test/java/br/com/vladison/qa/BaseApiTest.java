package br.com.vladison.qa;

import br.com.vladison.qa.product.ProductRepository;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApiTest {
    protected static final String API_KEY = "qa-secret-key";

    @LocalServerPort
    private int port;

    @Autowired
    protected ProductRepository productRepository;

    @BeforeEach
    void setUpApi() {
        productRepository.deleteAll();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";
        RestAssured.filters(new AllureRestAssured(), new ErrorLoggingFilter());
    }
}
