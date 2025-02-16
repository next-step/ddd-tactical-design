package kitchenpos.acceptance;

import io.restassured.RestAssured;
import kitchenpos.product.application.service.model.ChangeProductPriceRequest;
import kitchenpos.product.application.service.model.CreateProductRequest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductAcceptanceTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @DisplayName("`Product` 등록은 `상품 등록 정책`을 만족시켜야 한다")
    @SqlGroup({
            @Sql(value = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    void createProduct() {
        // 정상값 입력
        CreateProductRequest createProductRequest = new CreateProductRequest("후라이드 치킨", BigDecimal.valueOf(16_000L));
        given()
                .header("Content-Type", "application/json")
                .body(createProductRequest)
        .when()
                .post("/api/products")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(createProductRequest.getName()))
                .body("price", equalTo(createProductRequest.getPrice().intValue()))
                .body("id", notNullValue());
    }

    @DisplayName("`Product Price`를 변경할 수 있다")
    @SqlGroup({
            @Sql(value = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    void changeProductPrice() {
        // given
        CreateProductRequest createProductRequest = new CreateProductRequest("후라이드 치킨", BigDecimal.valueOf(16_000L));
        String productId = 상품을_등록한다(createProductRequest);

        // when : 정상적인 가격 변경
        BigDecimal price = BigDecimal.valueOf(18_000L);
        given()
                .header("Content-Type", "application/json")
                .body(new ChangeProductPriceRequest(price))
        .when()
                .put("/api/products/" + productId + "/price")
        .then()
                .statusCode(200)
                .body("price", equalTo(price.intValue()));

        // when : 가격이 음수인 경우
        given()
                .header("Content-Type", "application/json")
                .body(new ChangeProductPriceRequest(BigDecimal.valueOf(-1)))
        .when()
                .put("/api/products/" + productId + "/price")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    /*
     * Fixtures
     */
    private String 상품을_등록한다(CreateProductRequest createProductRequest) {
        return given()
                .header("Content-Type", "application/json")
                .body(createProductRequest)
        .when()
                .post("/api/products")
        .then()
                .extract()
                .path("id");
    }
}
