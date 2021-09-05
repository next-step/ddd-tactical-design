package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.Fixtures.DEFAULT_PRODUCT;
import static kitchenpos.menus.tobe.domain.Fixtures.MENU_PRODUCT_WITH_ALL;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

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
