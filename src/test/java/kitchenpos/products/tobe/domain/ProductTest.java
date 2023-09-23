package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;

class ProductTest {

    @Test
    void 상품을_등록할_수_있다() {
        assertDoesNotThrow(() -> new Product(UUID.randomUUID(), "치킨", 10000L, new FakePurgomalumClient()));
    }

    @Test
    void 가격을_변경할_수_있다() {
        Product product = new Product(UUID.randomUUID(), "꿀", 10000L, new FakePurgomalumClient());

        product.changePrice(new Price(15000L));

        assertThat(product.getPrice()).isEqualTo(new Price(15000L));
    }
}
