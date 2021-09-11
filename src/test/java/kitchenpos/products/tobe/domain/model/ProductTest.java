package kitchenpos.products.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.products.tobe.domain.fixture.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("상품은 식별자를 반환한다.")
    @Test
    void getId() {
        final UUID expected = UUID.randomUUID();
        final Product product = PRODUCT_WITH_ID(expected);

        assertThat(product.getId()).isEqualTo(expected);
    }

    @DisplayName("상품은 이름을 반환한다.")
    @ValueSource(strings = "후라이드")
    @ParameterizedTest
    void getName(final String name) {
        final Product product = PRODUCT_WITH_NAME(name);

        assertThat(product.getName()).isEqualTo(name);
    }

    @DisplayName("상품은 가격을 반환한다.")
    @ValueSource(strings = "16000")
    @ParameterizedTest
    void getPrice(final BigDecimal price) {
        final Product product = PRODUCT_WITH_PRICE(price);

        assertThat(product.getPrice()).isEqualTo(price);
    }

    @DisplayName("상품은 가격을 바꾼다.")
    @ValueSource(strings = "20000")
    @ParameterizedTest
    void changePrice(final BigDecimal expected) {
        final Price price = new Price(expected);
        final Product product = DEFAULT_PRODUCT();

        product.changePrice(price);

        assertThat(product.getPrice()).isEqualTo(expected);
    }
}
