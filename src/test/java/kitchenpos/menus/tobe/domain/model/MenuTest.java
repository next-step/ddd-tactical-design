package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.commons.tobe.domain.service.Validator;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.fixture.MenuFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    @DisplayName("메뉴를 생성한다.")
    @Test
    void createMenu() {
        final DisplayedName displayedName = new DisplayedName("후라이드치킨");
        final Price price = new Price(BigDecimal.valueOf(16_000L));
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, new Quantity(1L));
        final MenuProducts menuProducts = new MenuProducts(Collections.singletonList(menuProduct));
        final Validator<Menu> validator = menu -> {
        };

        final Menu menu = new Menu(
                UUID.randomUUID(),
                displayedName,
                price,
                menuProducts,
                UUID.randomUUID(),
                true,
                validator
        );

        assertAll(
                () -> assertThat(menu.getId()).isNotNull(),
                () -> assertThat(menu.getName()).isEqualTo(displayedName.value()),
                () -> assertThat(menu.getPrice()).isEqualTo(price.value()),
                () -> assertThat(menu.getProductIds()).containsSequence(menuProduct.getProductId()),
                () -> assertThat(menu.getMenuGroupId()).isNotNull(),
                () -> assertThat(menu.isDisplayed()).isTrue()
        );
    }

    @DisplayName("가격이 메뉴 상품들의 총 금액 보다 큰지 비교한다.")
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

    @DisplayName("메뉴를 노출할 때, 메뉴의 가격이 메뉴 상품들의 총 금액 보다 크면 IllegalStateException을 던진다.")
    @Test
    void throwExceptionWhenDisplay() {
        final Menu menu = NOT_DISPLAYED_MENU();

        final ThrowableAssert.ThrowingCallable when = menu::display;

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("가격은 메뉴 상품들의 총 금액 보다 적거나 같아야 합니다");
    }

    @DisplayName("메뉴는 숨겨진다.")
    @Test
    void hide() {
        final Menu menu = MENU_WITH_DISPLAYED(true);

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
