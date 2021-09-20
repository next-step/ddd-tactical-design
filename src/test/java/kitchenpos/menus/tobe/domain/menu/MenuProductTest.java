package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import kitchenpos.products.domain.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성할 수 있다.")
    @Test
    void 생성() {
        final Product product = ToBeFixtures.product();
        assertDoesNotThrow(
            () -> ToBeFixtures.menuProduct(product.getId(), product.getPrice(), 2L)
        );
    }

    @DisplayName("메뉴 상품 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final Long seq = 1L;

        final MenuProduct menuProduct1 = ToBeFixtures.menuProduct(
            seq,
            new ProductId(UUID.randomUUID()),
            new Price(BigDecimal.valueOf(16_000L)),
            1L
        );
        final MenuProduct menuProduct2 = ToBeFixtures.menuProduct(
            seq,
            new ProductId(UUID.randomUUID()),
            new Price(BigDecimal.valueOf(16_000L)),
            1L
        );

        assertThat(menuProduct1).isEqualTo(menuProduct2);
    }
}
