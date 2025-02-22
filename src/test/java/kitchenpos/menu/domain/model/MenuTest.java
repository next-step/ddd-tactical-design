package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuPriceValidationException;
import kitchenpos.menu.domain.exception.MenuProductValidationException;
import kitchenpos.menu.domain.exception.MenuValidationException;
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
    @DisplayName("Menu 생성 테스트")
    class CreateMenuTest {

        @DisplayName("`Menu`를 생성할 수 있다")
        @Test
        void createMenuTest() {
            // given
            final MenuGroup menuGroup = createMenuGroup();
            final List<MenuProduct> menuProductList = createMenuProducts();

            // when
            final Menu menu = createMenu(BigDecimal.valueOf(16_000), menuGroup, menuProductList);

            // then
            assertThat(menu.getName()).isEqualTo("후라이드 치킨");
            assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(16_000));
            assertThat(menu.isDisplayed()).isFalse();
            assertThat(menu.getMenuGroup()).isEqualTo(menuGroup);
            assertThat(menu.getMenuProducts()).isEqualTo(menuProductList);
        }

        @DisplayName("`Menu`는 `Menu Products`의 `Total Product Price` 보다 `Menu Price`가 작아야 한다")
        @Test
        void hideMenuIfMenuPriceIsGreaterThanTotalProductPrice() {
            // given
            final MenuGroup menuGroup = createMenuGroup();
            final List<MenuProduct> menuProductList = createMenuProducts();

            // when
            ThrowableAssert.ThrowingCallable throwable = () -> createMenu(BigDecimal.valueOf(20_000), menuGroup, menuProductList);

            // then
            assertThatThrownBy(throwable)
                .isInstanceOf(MenuPriceValidationException.class)
                .hasMessage("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }

        @DisplayName("`Menu`는 `Menu Products`를 포함해야 한다")
        @Test
        void menuShouldContainMenuProducts() {
            // given
            final MenuGroup menuGroup = createMenuGroup();

            // when
            ThrowableAssert.ThrowingCallable throwable = () -> createMenu(BigDecimal.valueOf(20_000), menuGroup, List.of());

            // then
            assertThatThrownBy(throwable)
                .isInstanceOf(MenuProductValidationException.class)
                .hasMessage("메뉴 상품은 1개 이상 입력해야 합니다.");
        }

        @DisplayName("`Menu`는 하나 이상의 `Menu Group`에 속한다")
        @Test
        void menuShouldBelongToOneOrMoreMenuGroups() {
            // given
            final List<MenuProduct> menuProductList = createMenuProducts();
            final MenuGroup menuGroup = null;

            // when
            ThrowableAssert.ThrowingCallable throwable = () -> createMenu(BigDecimal.valueOf(20_000), menuGroup, menuProductList);

            // then
            assertThatThrownBy(throwable)
                .isInstanceOf(MenuValidationException.class)
                .hasMessage("메뉴 그룹을 반드시 선택해야 합니다.");
        }
    }

    @Nested
    @DisplayName("Menu Price 변경 테스트")
    class ChangeMenuPriceTest {

        @DisplayName("`Menu Price`를 변경할 수 있다")
        @Test
        void changeMenuPrice() {
            // given
            final Menu menu = createMenu();

            // when
            menu.changePrice(BigDecimal.valueOf(10_000));

            // then
            assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(10_000));
        }

        @DisplayName("`Menu Price`가 `Menu Products`의 `Total Product Price` 보다 작아야 한다.")
        @Test
        void hideMenuIfMenuPriceIsGreaterThanTotalProductPrice() {
            // given
            final Menu menu = createMenu();

            // when
            ThrowableAssert.ThrowingCallable throwable = () -> menu.changePrice(BigDecimal.valueOf(20_000));

            // then
            assertThatThrownBy(throwable)
                    .isInstanceOf(MenuPriceValidationException.class)
                    .hasMessage("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }

    }

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
        return createMenu(BigDecimal.valueOf(16_000), createMenuGroup(), createMenuProducts());
    }

    private static Menu createMenu(BigDecimal menuPrice, MenuGroup menuGroup, List<MenuProduct> menuProductList) {
        return Menu.create(
                "후라이드 치킨",
                menuPrice,
                false,
                menuGroup,
                menuProductList,
                name -> false
        );
    }

    private static void updateFirstMenuProductPrice(Menu menu, int price) {
        menu.changeMenuProductPrice(menu.getMenuProducts().get(0).getProductId(), BigDecimal.valueOf(price));
    }

    private static MenuGroup createMenuGroup() {
        return MenuGroup.create(UUID.randomUUID(), "치킨", name -> false);
    }

    private static List<MenuProduct> createMenuProducts() {
        return List.of(
                new MenuProduct(1L, UUID.randomUUID(), 1, BigDecimal.valueOf(16_000))
        );
    }
}
