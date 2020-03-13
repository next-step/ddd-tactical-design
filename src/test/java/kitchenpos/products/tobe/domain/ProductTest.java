package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("제품을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final String name = "새로운 제품";
        final BigDecimal price = BigDecimal.valueOf(1000);

        // when
        Product product = new Product(name, price);

        // then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }
}
