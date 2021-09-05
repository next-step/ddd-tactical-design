package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.Fixtures.DEFAULT_PRODUCT;
import static kitchenpos.menus.tobe.domain.Fixtures.MENU_PRODUCT_WITH_ALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {

    @DisplayName("메뉴 상품에는 상품이 필수다.")
    @Test
    void MenuProduct() {
        assertThatThrownBy(() -> MENU_PRODUCT_WITH_ALL(null, 0)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품은 필수입니다");
    }

    @DisplayName("메뉴 상품은 금액을 반환한다.")
    @ValueSource(longs = {0, 1, 2})
    @ParameterizedTest
    void getAmount(final long quantity) {
        final Product product = DEFAULT_PRODUCT();
        final MenuProduct menuProduct = MENU_PRODUCT_WITH_ALL(product, quantity);

        final BigDecimal expected = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        assertThat(menuProduct.getAmount()).isEqualTo(expected);
    }
}
