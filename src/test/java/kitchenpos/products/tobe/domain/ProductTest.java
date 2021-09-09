package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    private static final Profanities profanities = new FakeProfanities();

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> createProduct("치킨", 16_000L)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void 가격변경() {
        final Product product = createProduct("치킨", 16_000L);
        final Price newPrice = new Price(BigDecimal.valueOf(20_000L));

        product.changePrice(newPrice);

        assertThat(product.getPrice()).isEqualTo(newPrice);
    }

    @DisplayName("상품 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final UUID id = UUID.randomUUID();

        final Product product1 = createProduct(id, "치킨", 16_000L);
        final Product product2 = createProduct(id, "치킨", 16_000L);

        assertThat(product1).isEqualTo(product2);
    }

    private Product createProduct(UUID id, final String displayedName, final Long price) {
        return new Product(
            id,
            new DisplayedName(displayedName, profanities),
            new Price(BigDecimal.valueOf(price))
        );
    }

    private Product createProduct(final String displayedName, final Long price) {
        return createProduct(UUID.randomUUID(), displayedName, price);
    }
}
