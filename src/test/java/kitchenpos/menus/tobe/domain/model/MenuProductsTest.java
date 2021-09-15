package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static kitchenpos.menus.tobe.domain.fixture.MenuProductFixture.MENU_PRODUCT_WITH_PRICE_AND_QUANTITY;
import static kitchenpos.menus.tobe.domain.fixture.MenuProductsFixture.MENU_PRODUCTS_WITH_MENU_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성한다.")
    @Test
    void createMenuProducts() {
        final Price price = new Price(BigDecimal.valueOf(16_000L));
        final Quantity quantity = new Quantity(1L);
        final MenuProduct menuProduct1 = new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, quantity);
        final MenuProduct menuProduct2 = new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, quantity);

        final MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct1, menuProduct2));

        assertThat(menuProducts.getProductIds()).containsSequence(menuProduct1.getProductId(), menuProduct2.getProductId());
    }

    @DisplayName("메뉴 상품 목록은 메뉴 상품들의 총 금액을 반환한다.")
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
