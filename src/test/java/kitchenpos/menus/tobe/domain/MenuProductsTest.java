package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.fixtures.MenuProductsFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.fixtures.MenuProductFixture.MENU_PRODUCT_WITH_PRICE_AND_QUANTITY;
import static kitchenpos.menus.tobe.domain.fixtures.MenuProductsFixture.MENU_PRODUCTS_WITH_MENU_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록은 1 개 이상의 메뉴 상품을 가져야 한다.")
    @Test
    void createMenuProducts() {
        final ThrowableAssert.ThrowingCallable when = MenuProductsFixture::MENU_PRODUCTS_WITH_MENU_PRODUCT;

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 개 이상의 메뉴 상품을 가져야 합니다");
    }

    @DisplayName("메뉴 상품 목록은 금액을 반환한다.")
    @Test
    void getAmount() {
        final BigDecimal price1 = BigDecimal.valueOf(7_000L);
        final BigDecimal price2 = BigDecimal.valueOf(6_500L);
        final long quantity1 = 1L;
        final long quantity2 = 2L;
        final BigDecimal amount1 = price1.multiply(BigDecimal.valueOf(quantity1));
        final BigDecimal amount2 = price2.multiply(BigDecimal.valueOf(quantity2));
        final BigDecimal expected = amount1.add(amount2);

        final MenuProducts menuProducts = MENU_PRODUCTS_WITH_MENU_PRODUCT(
                MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price1, quantity1),
                MENU_PRODUCT_WITH_PRICE_AND_QUANTITY(price2, quantity2)
        );

        assertThat(menuProducts.getAmount()).isEqualTo(expected);
    }
}
