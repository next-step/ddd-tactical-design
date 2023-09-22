package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.DisplayedName;
import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @Test
    void 상품을_등록할_수_있다() {
        assertDoesNotThrow(() -> new Product(UUID.randomUUID(), new DisplayedName("치킨", new FakePurgomalumClient()), 10000L));
    }

    @Test
    void 가격을_변경할_수_있다() {
        Product product = new Product(UUID.randomUUID(), new DisplayedName("꿀", new FakePurgomalumClient()), 10000L);

        product.changePrice(new Price(15000L));

        assertThat(product.getPrice()).isEqualTo(new Price(15000L));
    }
}
