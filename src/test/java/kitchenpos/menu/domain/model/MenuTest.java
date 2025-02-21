package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuPriceValidationException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    @Nested
    @DisplayName("Menu Display, Hide 테스트")
    class MenuDisplayHideTest {

        @DisplayName("`Menu`는 `Display Menu`로 변경할 수 있다")
        @Test
        void changeMenuToDisplayMenu() {
            // given
            final Menu menu = createMenu();

            // when
            menu.display();

            // then
            assertThat(menu.isDisplayed()).isTrue();
        }

        @DisplayName("`Menu Price`가 `Menu Products`의 `Total Product Price` 보다 작아야 한다.")
        @Test
        void menuPriceShouldBeLessThanOrEqualToTotalProductPrice() {
            // given
            final Menu menu = createMenu();
            updateFirstMenuProductPrice(menu, 10_000);

            // when
            ThrowableAssert.ThrowingCallable throwable = menu::display;

            // then
            assertThatThrownBy(throwable)
                    .isInstanceOf(MenuPriceValidationException.class)
                    .hasMessage("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }

        @DisplayName("`Menu`는 `Hide`로 변경할 수 있다")
        @Test
        void changeMenuToHide() {
            // given
            final Menu menu = createMenu();

            // when
            menu.hide();

            // then
            assertThat(menu.isDisplayed()).isFalse();
        }
    }

    private static Menu createMenu() {
        return Menu.create(
            "후라이드 치킨",
            BigDecimal.valueOf(16_000),
            false,
            MenuGroup.create(UUID.randomUUID(), "치킨", name -> false),
            List.of(
                new MenuProduct(1L, UUID.randomUUID(), 1, BigDecimal.valueOf(16_000))
            ),
            name -> false
        );
    }

    private static void updateFirstMenuProductPrice(Menu menu, int price) {
        menu.changeMenuProductPrice(menu.getMenuProducts().get(0).getProductId(), BigDecimal.valueOf(price));
    }
}
