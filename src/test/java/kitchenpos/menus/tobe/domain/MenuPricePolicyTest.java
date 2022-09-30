package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuPricePolicyTest {
    private MenuPricePolicy menuPriceChangeService;
    private List<Integer> productsPrice;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menuPriceChangeService = new MenuPricePolicy();

        menu = new Menu("후라이드+양념", 40000, 1L,
                List.of(new MenuProduct(10L, 1), new MenuProduct(11L, 1)));

        productsPrice = List.of(20000, 22000);
    }

    @DisplayName("메뉴의 가격이 메뉴 상품 목록의 가격의 합보다 작아야 한다.")
    @Test
    void changePrice() {
        menu.changePrice(39000);

        assertDoesNotThrow(() -> menuPriceChangeService.validate(menu, productsPrice));
    }

    @DisplayName("메뉴의 가격이 메뉴 상품 목록의 가격의 합 이상이면 예외가 발생한다.")
    @Test
    void changePriceWithWrongPrice() {
        menu.changePrice(44000);

        assertThatThrownBy(() -> menuPriceChangeService.validate(menu, productsPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
