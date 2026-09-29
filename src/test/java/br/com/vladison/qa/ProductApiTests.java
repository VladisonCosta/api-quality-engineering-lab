package br.com.vladison.qa;

import br.com.vladison.qa.product.Product;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Epic("Product API")
@Feature("CRUD and contract validation")
@DisplayName("Product API Quality Suite")
class ProductApiTests extends BaseApiTest {

    private Map<String, Object> validProduct(String sku) {
        return Map.of("name", "Mechanical Keyboard", "sku", sku, "price", 299.90, "stock", 15);
    }

    private Long seedProduct(String sku) {
        return productRepository.save(new Product("QA Mouse", sku, new BigDecimal("149.90"), 8)).getId();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Authentication")
    @DisplayName("POST /products without API key returns 401")
    void shouldRejectMutationWithoutApiKey() {
        given().contentType(ContentType.JSON).body(validProduct("KEY-001"))
        .when().post("/products")
        .then().statusCode(401)
               .contentType(ContentType.JSON)
               .body("error", equalTo("Unauthorized"))
               .body("message", containsString("X-API-Key"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create product")
    @DisplayName("POST /products creates a valid product")
    void shouldCreateProduct() {
        given().header("X-API-Key", API_KEY).contentType(ContentType.JSON).body(validProduct("KEY-002"))
        .when().post("/products")
        .then().statusCode(201)
               .header("Location", matchesPattern(".*/api/products/\\d+"))
               .contentType(ContentType.JSON)
               .body("id", notNullValue())
               .body("name", equalTo("Mechanical Keyboard"))
               .body("sku", equalTo("KEY-002"))
               .body("price", comparesEqualTo(299.9f))
               .body("stock", equalTo(15));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Validation")
    @DisplayName("POST /products rejects missing required fields")
    void shouldRejectInvalidPayload() {
        Map<String, Object> invalid = Map.of("name", "", "sku", "ab", "price", 0, "stock", -1);
        given().header("X-API-Key", API_KEY).contentType(ContentType.JSON).body(invalid)
        .when().post("/products")
        .then().statusCode(400)
               .body("message", equalTo("Validation failed"))
               .body("validationErrors.name", notNullValue())
               .body("validationErrors.sku", notNullValue())
               .body("validationErrors.price", notNullValue())
               .body("validationErrors.stock", notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Business rule")
    @DisplayName("POST /products rejects duplicate SKU")
    void shouldRejectDuplicateSku() {
        seedProduct("DUP-001");
        given().header("X-API-Key", API_KEY).contentType(ContentType.JSON).body(validProduct("DUP-001"))
        .when().post("/products")
        .then().statusCode(409)
               .body("error", equalTo("Conflict"))
               .body("message", containsString("SKU already exists"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Read product")
    @DisplayName("GET /products/{id} returns existing product")
    void shouldGetProductById() {
        Long id = seedProduct("GET-001");
        given().pathParam("id", id)
        .when().get("/products/{id}")
        .then().statusCode(200)
               .body("id", equalTo(id.intValue()))
               .body("sku", equalTo("GET-001"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Read product")
    @DisplayName("GET /products/{id} returns 404 for missing product")
    void shouldReturnNotFound() {
        given().pathParam("id", 999999)
        .when().get("/products/{id}")
        .then().statusCode(404)
               .body("error", equalTo("Not Found"))
               .body("message", containsString("Product not found"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Pagination")
    @DisplayName("GET /products supports pagination")
    void shouldListProductsWithPagination() {
        seedProduct("PAGE-001");
        seedProduct("PAGE-002");
        seedProduct("PAGE-003");

        given().queryParam("page", 0).queryParam("size", 2).queryParam("sort", "id,asc")
        .when().get("/products")
        .then().statusCode(200)
               .body("content", hasSize(2))
               .body("totalElements", equalTo(3))
               .body("totalPages", equalTo(2))
               .body("size", equalTo(2));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Update product")
    @DisplayName("PUT /products/{id} updates an existing product")
    void shouldUpdateProduct() {
        Long id = seedProduct("UPD-001");
        Map<String, Object> body = Map.of("name", "Updated Mouse", "sku", "UPD-001", "price", 179.90, "stock", 20);

        given().header("X-API-Key", API_KEY).contentType(ContentType.JSON).body(body).pathParam("id", id)
        .when().put("/products/{id}")
        .then().statusCode(200)
               .body("name", equalTo("Updated Mouse"))
               .body("stock", equalTo(20));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete product")
    @DisplayName("DELETE /products/{id} removes an existing product")
    void shouldDeleteProduct() {
        Long id = seedProduct("DEL-001");
        given().header("X-API-Key", API_KEY).pathParam("id", id)
        .when().delete("/products/{id}")
        .then().statusCode(204).body(emptyOrNullString());

        given().pathParam("id", id)
        .when().get("/products/{id}")
        .then().statusCode(404);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Contract")
    @DisplayName("GET /products/{id} follows the JSON Schema contract")
    void shouldMatchProductJsonSchema() {
        Long id = seedProduct("SCH-001");
        given().pathParam("id", id)
        .when().get("/products/{id}")
        .then().statusCode(200)
               .body(matchesJsonSchemaInClasspath("schemas/product-response.json"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("HTTP contract")
    @DisplayName("GET /products/{id} exposes expected content type")
    void shouldReturnJsonContentType() {
        Long id = seedProduct("HDR-001");
        given().pathParam("id", id)
        .when().get("/products/{id}")
        .then().statusCode(200)
               .contentType(ContentType.JSON);
    }
}
