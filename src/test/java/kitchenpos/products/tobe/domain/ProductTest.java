package kitchenpos.products.tobe.domain;

import kitchenpos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() {
        assertDoesNotThrow(
                () -> ProductFixture.상품()
        );
    }

    @Test
    @DisplayName("상품의 가격을 변경할 수 있다.")
    void changePrice() {

    }



}