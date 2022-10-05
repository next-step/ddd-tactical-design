package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @DisplayName("가격은 0원 이상으로만 변경할 수 있다.")
    @Test
    void changePrice_Exception() {
        Product product = new Product(UUID.randomUUID(), "후라이드 치킨", 9_000L);

        assertThatThrownBy(() -> product.changePrice(-1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격은 0원 이상으로 변경하면 ProductPriceChangedEvent가 발행된다.")
    @Test
    void changePrice() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "후라이드 치킨", 9_000L);

        ProductPriceChangedEvent event = product.changePrice(10_000L);

        assertAll(
                () -> assertThat(event.getProductId()).isEqualTo(productId),
                () -> assertThat(event.getChangedPrice()).isEqualTo(10_000L)
        );
    }

    @DisplayName("식별자가 같으면 동등한 상품이다.")
    @Test
    void equals() {
        UUID id = UUID.randomUUID();

        assertThat(new Product(id, "후라이드 치킨", 9_000L))
                .isEqualTo(new Product(id, "Fried Chicken", 9_000L));
    }
}
