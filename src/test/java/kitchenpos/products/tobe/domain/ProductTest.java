package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    @DisplayName("상품을 생성한다.")
    void create() {
        DisplayedName displayedName = DisplayedName.of("상품", (name -> false));
        Price price = new Price(BigDecimal.valueOf(1000));
        Product product = new Product(displayedName, price);
        assertThat(product).isNotNull();
    }
}
