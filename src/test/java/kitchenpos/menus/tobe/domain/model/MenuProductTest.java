package kitchenpos.menus.tobe.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.fixture.MenuProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

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
