package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.Fixtures.PRODUCT_WITH_ID;
import static kitchenpos.menus.tobe.domain.Fixtures.PRODUCT_WITH_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("상품은 식별자를 반환한다.")
    @Test
    void getId() {
        final UUID expected = UUID.randomUUID();
        final Product product = PRODUCT_WITH_ID(expected);

        assertThat(product.getId()).isEqualTo(expected);
    }

    @DisplayName("상품은 가격을 반환한다.")
    @ValueSource(strings = "16000")
    @ParameterizedTest
    void getPrice(final BigDecimal price) {
        final Product product = PRODUCT_WITH_PRICE(price);

        assertThat(product.getPrice()).isEqualTo(price);
    }
}
