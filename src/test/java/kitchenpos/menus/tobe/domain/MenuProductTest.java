package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.fixtures.MenuProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {

    @DisplayName("메뉴 상품에는 메뉴 식별자가 필수다.")
    @Test
    void createMenuProductWithoutMenuId() {
        final ThrowableAssert.ThrowingCallable when = () -> MENU_PRODUCT_WITH_MENU_ID(null);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 식별자는 필수입니다");
    }

    @DisplayName("메뉴 상품에는 상품 식별자가 필수다.")
    @Test
    void createMenuProductWithoutProductId() {
        final ThrowableAssert.ThrowingCallable when = () -> MENU_PRODUCT_WITH_PRODUCT_ID(null);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 식별자는 필수입니다");
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
