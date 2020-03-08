package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.products.ProductFixtures.friedChicken;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("상품 생성")
    @Test
    void create() {
        Product product = friedChicken();
        assertThat(product).isNotNull();
    }
}
