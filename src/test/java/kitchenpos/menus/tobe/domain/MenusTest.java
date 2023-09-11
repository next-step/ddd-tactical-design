package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.menus.tobe.domain.MenuTest.createMenu;
import static org.assertj.core.api.Assertions.assertThat;

class MenusTest {

    @Test
    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높은 경우 메뉴를 비노출한다")
    void hideMenuIfPriceExceedsSum() {
        Menu menu = createMenu(17_000L);
        Menus menus = new Menus(List.of(menu));

        menus.hideMenuIfPriceExceedsSum();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높지 않다면 메뉴를 노출한다")
    void showMenuIfPriceFallsSum() {
        Menu menu = createMenu(16_000L);
        Menus menus = new Menus(List.of(menu));

        menus.hideMenuIfPriceExceedsSum();

        assertThat(menu.isDisplayed()).isTrue();
    }
}
