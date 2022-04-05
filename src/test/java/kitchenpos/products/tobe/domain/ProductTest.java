package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void changePrice() {
        // given
        Product product = new Product(UUID.randomUUID(), "짜장면", BigDecimal.valueOf(6_000));

        // when
        product.changePrice(BigDecimal.valueOf(7_000));

        // then
        assertThat(product.getPrice()).isEqualTo(new ProductPrice(7_000));
    }
}