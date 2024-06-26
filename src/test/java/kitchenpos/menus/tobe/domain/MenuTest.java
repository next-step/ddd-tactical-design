package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import kitchenpos.products.tobe.ProductPrice;
import kitchenpos.products.tobe.ProductPrices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MenuTest {

    @DisplayName("메뉴 생성")
    @Test
    void createMenu_successTest() {

        assertDoesNotThrow(() -> {
            new Menu(UUID.randomUUID(), new Name("메뉴"), Money.from(1000L), new MenuGroup(), true, List.of());
        });
    }

    @DisplayName("메뉴 보이기")
    @Test
    void displaySuccessTest() {

        // given
        final var p1 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(1000));
        final var p2 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(2000));
        final var productPrices = new ProductPrices(List.of(p1, p2));

        final var menuProducts = List.of(
                new MenuProduct(p1.getProductId(), 1),
                new MenuProduct(p2.getProductId(), 1)
        );


        Menu menu = new Menu(UUID.randomUUID(), new Name("메뉴"), Money.from(1000L), new MenuGroup(), true, menuProducts);

        // when
        menu.display(productPrices);

        // then
        assertTrue(menu.isDisplayed());
    }

    @DisplayName("메뉴 보이기 실패")
    @Test
    void displayFailTest() {

        // given
        final var p1 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(1000));
        final var p2 = new ProductPrice(UUID.randomUUID(), BigDecimal.valueOf(2000));
        final var productPrices = new ProductPrices(List.of(p1, p2));

        final var menuProducts = List.of(
                new MenuProduct(p1.getProductId(), 1),
                new MenuProduct(p2.getProductId(), 1)
        );


        Menu menu = new Menu(UUID.randomUUID(), new Name("메뉴"), Money.from(Long.MAX_VALUE), new MenuGroup(), true, menuProducts);

        // when
        // then
        assertThatThrownBy(() -> menu.display(productPrices))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴에 포함된 상품의 가격이 상품의 가격보다 높습니다.");
    }
}