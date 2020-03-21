package kitchenpos.products.todo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductPriceTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"0", "-1000", "-10000"})
    @DisplayName("상품의 가격은 0 원 이상이어야 한다.")
    void verifyPriceTest(BigDecimal price) {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductPrice(price));
    }
}