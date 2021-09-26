package kitchenpos.products.tobe.domain;

import kitchenpos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() {
        assertDoesNotThrow(
                () -> ProductFixture.상품()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = "3000")
    @DisplayName("상품의 가격을 변경할 수 있다.")
    void changePrice(final BigDecimal price) {
        final Product product = ProductFixture.상품();
        final Price newPrice = new Price(price);

        product.changePrice(newPrice);

        assertThat(product.getPrice()).isEqualTo(newPrice);
    }
}
