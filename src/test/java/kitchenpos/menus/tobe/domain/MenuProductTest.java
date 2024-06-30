package kitchenpos.menus.tobe.domain;


import kitchenpos.menus.tobe.application.MenuProducts;
import kitchenpos.products.tobe.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MenuProductTest {

    @Test
    @DisplayName("수량이 0보다 작은 경우 예외가 발생한다.")
    public void createFailTest() {

        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProduct(null, -1, Money.from(1_000)))
                .withMessage("수량은 0보다 작을 수 없습니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createFailWhenPriceIsHigherThanProductPriceTest() {

        final var menuProducts = List.of(
                new MenuProduct(UUID.randomUUID(), 1, Money.from(1_000)),
                new MenuProduct(UUID.randomUUID(), 1, Money.from(2_000))
        );

        assertThatIllegalArgumentException().isThrownBy(() -> MenuProducts.of(menuProducts, Money.from(Long.MAX_VALUE)))
                .withMessage("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");

    }
}