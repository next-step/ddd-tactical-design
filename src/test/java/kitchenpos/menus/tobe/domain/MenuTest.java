package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.tobe.domain.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

class MenuTest {

    private static final String PRICE_AMOUNT_EXCEPTION_MESSAGE = "가격은 금액보다 적거나 같아야 합니다";

    @DisplayName("메뉴 생성 시, 숨겨진 메뉴가 아니라면, 메뉴의 가격이 메뉴 상품 목록의 금액보다 크면 IllegalArgumentException을 던진다.")
    @Test
    void createMenuWithIllegalPrice() {
        final Product product = PRODUCT_WITH_PRICE(BigDecimal.valueOf(1_000L));
        final BigDecimal price = BigDecimal.valueOf(1_500L);

        final ThrowableAssert.ThrowingCallable when = () -> MENU_WITH_PRICE_AND_ONLY_ONE_PRODUCT(price, product);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRICE_AMOUNT_EXCEPTION_MESSAGE);
    }

    @DisplayName("메뉴 생성 시, 메뉴 그룹 식별자가 없으면 IllegalArgumentException을 던진다.")
    @Test
    void createMenuWithoutMenuGroupId() {
        final ThrowableAssert.ThrowingCallable when = Fixtures::MENU_WITHOUT_MENU_GROUP_ID;

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴는 메뉴 그룹에 속해야 합니다");
    }

    @DisplayName("메뉴는 노출된다.")
    @Test
    void display() {
        final Menu menu = DEFAULT_MENU();

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 노출할 때, 숨겨진 메뉴가 아니라면, 메뉴의 가격이 메뉴 상품 목록의 금액보다 크면 IllegalStateException을 던진다.")
    @Test
    void throwExceptionWhenDisplay() {
        final Menu menu = HIDED_MENU();

        final ThrowableAssert.ThrowingCallable when = menu::display;

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage(PRICE_AMOUNT_EXCEPTION_MESSAGE);
    }

    @DisplayName("메뉴는 숨겨진다.")
    @Test
    void hide() {
        final Menu menu = DEFAULT_MENU();

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
