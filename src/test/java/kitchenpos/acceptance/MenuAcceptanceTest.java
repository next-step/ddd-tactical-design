package kitchenpos.acceptance;

import io.restassured.RestAssured;
import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.NoSuchElementException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("상품 가격이 변경되어 메뉴의 가격이 메뉴상품의 총합보다 높아지면 메뉴를 숨김 처리한다")
    @Test
    @SqlGroup({
            @Sql(value = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void hideMenuWhenPriceIsHigherThanSumOfMenuProducts() {
        // given
        String menuId = "f59b1e1c-b145-440a-aa6f-6095a0e2d63b";
        String productId = "3b528244-34f7-406b-bb7e-690912f66b10";
        long price = 10_000L;

        // when
        상품의_가격을_변경한다(productId, price);

        // then
        Menu menu = menuRepository.findById(UUID.fromString(menuId)).orElseThrow(NoSuchElementException::new);
        assertThat(menu.isDisplayed()).isFalse();
    }

    /*
     * Fixtures
     */

    private void 상품의_가격을_변경한다(String productId, long price) {
        given()
                .header("Content-Type", "application/json")
                .body("{\"price\": " + price + "}")
                .pathParam("productId", productId)
        .when()
                .put("/api/products/{productId}/price")
        .then()
                .statusCode(200);
    }
}
