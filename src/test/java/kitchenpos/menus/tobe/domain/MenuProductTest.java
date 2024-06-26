package kitchenpos.menus.tobe.domain;


import kitchenpos.menus.tobe.application.MenuProducts;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.ProductPrice;
import kitchenpos.products.tobe.ProductPrices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MenuProductTest {

    @Test
    @DisplayName("수량이 0보다 작은 경우 예외가 발생한다.")
    public void createFailTest() {

        assertThatThrownBy(() -> new MenuProduct(null, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 0보다 작을 수 없습니다.");
    }

    @DisplayName("메뉴상품의 가격이 상품 가격보다 높으면 예외가 발생한다.")
    @Test
    void createFailWhenPriceIsHigherThanProductPriceTest() {

        final var p1 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(1000));
        final var p2 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(2000));
        final var productPrices = new ProductPrices(List.of(p1, p2));

        final var menuProducts = List.of(
                new MenuProduct(p1.getProductId(), 1),
                new MenuProduct(p2.getProductId(), 1)
        );


        assertThatThrownBy(() -> MenuProducts.of(menuProducts, Money.from(Long.MAX_VALUE), productPrices))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴에 포함된 상품의 가격이 상품의 가격보다 높습니다.");
    }
}