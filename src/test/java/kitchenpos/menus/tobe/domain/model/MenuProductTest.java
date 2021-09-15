package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.fixture.MenuProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void createMenuProduct() {
        final Price price = new Price(BigDecimal.valueOf(16_000L));
        final Quantity quantity = new Quantity(1L);

        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(),UUID.randomUUID(), price, quantity);

        assertAll(
                () -> assertThat(menuProduct.getId()).isNotNull(),
                () -> assertThat(menuProduct.getProductId()).isNotNull()
        );
    }

    @DisplayName("메뉴 상품은 금액을 반환한다.")
    @ValueSource(longs = {0, 1, 2})
    @ParameterizedTest
    void getAmount(final long quantity) {
        final BigDecimal price = BigDecimal.valueOf(16_000L);
        final MenuProduct menuProduct = MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price, quantity);

        final BigDecimal expected = price.multiply(BigDecimal.valueOf(quantity));

        assertThat(menuProduct.getAmount()).isEqualTo(expected);
    }
}
