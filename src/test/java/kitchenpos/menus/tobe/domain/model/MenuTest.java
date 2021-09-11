package kitchenpos.menus.tobe.domain.model;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.menus.tobe.domain.fixture.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @DisplayName("가격이 금액 보다 큰지 비교한다.")
    @Test
    void isPriceGreaterThanAmount() {
        final Menu menu = NOT_DISPLAYED_MENU();

        assertThat(menu.isPriceGreaterThanAmount()).isTrue();
    }


    @DisplayName("메뉴는 노출된다.")
    @Test
    void display() {
        final Menu menu = MENU_WITH_DISPLAYED(false);

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 노출할 때, 메뉴의 가격이 메뉴 상품 목록의 금액보다 크면 IllegalStateException을 던진다.")
    @Test
    void throwExceptionWhenDisplay() {
        final Menu menu = NOT_DISPLAYED_MENU();

        final ThrowableAssert.ThrowingCallable when = menu::display;

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("가격은 금액보다 적거나 같아야 합니다");
    }

    @DisplayName("메뉴는 숨겨진다.")
    @Test
    void hide() {
        final Menu menu = MENU_WITH_DISPLAYED(true);

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
