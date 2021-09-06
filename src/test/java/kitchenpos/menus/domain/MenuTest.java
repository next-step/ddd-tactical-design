package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다")
    @Test
    void checkDisplay() {
        Menu menu = menu(100_000L, true,
                menuProduct(),
                menuProduct());

        menu.checkDisplay();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
