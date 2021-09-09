package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void 생성() {
        final UUID id = UUID.randomUUID();
        final DisplayedName displayedName = new DisplayedName("치킨", new FakeProfanities());
        final Price price = new Price(BigDecimal.valueOf(18000L));

        assertDoesNotThrow(
            () -> new Product(id, displayedName, price)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void 가격변경() {
        final Product product = new Product(
            UUID.randomUUID(),
            new DisplayedName("치킨", new FakeProfanities()),
            new Price(BigDecimal.valueOf(18000L))
        );
        final Price newPrice = new Price(BigDecimal.valueOf(20000L));

        product.changePrice(newPrice);

        assertThat(product.getPrice()).isEqualTo(newPrice);
    }

    @DisplayName("상품 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final UUID id = UUID.randomUUID();
        final DisplayedName displayedName = new DisplayedName("치킨", new FakeProfanities());
        final Price price = new Price(BigDecimal.valueOf(18000L));

        final Product product1 = new Product(id, displayedName, price);
        final Product product2 = new Product(id, displayedName, price);

        assertThat(product1).isEqualTo(product2);
    }
}
