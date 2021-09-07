package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.Fixtures.*;
import static kitchenpos.menus.tobe.domain.Fixtures.MENU_PRODUCT_WITH_ALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록은 1 개 이상의 메뉴 상품을 가져야 한다.")
    @Test
    void createMenuProducts() {
        final ThrowableAssert.ThrowingCallable when = Fixtures::MENU_PRODUCTS_WITH_MENU_PRODUCT;

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 개 이상의 메뉴 상품을 가져야 합니다");
    }

    @DisplayName("메뉴 상품 목록은 금액을 반환한다.")
    @Test
    void getAmount() {
        final Product product1 = PRODUCT_WITH_PRICE(BigDecimal.valueOf(7_000L));
        final Product product2 = PRODUCT_WITH_PRICE(BigDecimal.valueOf(6_500L));
        final long quantity1 = 1L;
        final long quantity2 = 2L;
        final MenuProducts menuProducts = MENU_PRODUCTS_WITH_MENU_PRODUCT(
                MENU_PRODUCT_WITH_ALL(product1, quantity1),
                MENU_PRODUCT_WITH_ALL(product2, quantity2)
        );

        final BigDecimal amount1 = product1.getPrice().multiply(BigDecimal.valueOf(quantity1));
        final BigDecimal amount2 = product2.getPrice().multiply(BigDecimal.valueOf(quantity2));
        final BigDecimal expected = amount1.add(amount2);

        assertThat(menuProducts.getAmount()).isEqualTo(expected);
    }
}
