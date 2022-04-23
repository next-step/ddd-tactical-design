package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import kitchenpos.fakeobject.FakeMenuPurgomalumClient;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Nested
    @DisplayName("메뉴 생성")
    class CreateMenu {

        @DisplayName("메뉴에 속한 상품의 총 금액 합보다 메뉴 금액이 크다면 생성할 수 없다.")
        @Test
        void menu_price_over_menu_products_price() {
            MenuPrice price = new MenuPrice(BigDecimal.valueOf(10_000_000));
            MenuName name = new MenuName(new FakeMenuPurgomalumClient(), "메뉴 이름");
            MenuProducts menuProducts = new MenuProducts(Arrays.asList(new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, 2L)));
            MenuGroup menuGroup = new MenuGroup("메뉴 그룹 이름", new FakeMenuPurgomalumClient());

            assertThatThrownBy(() -> new Menu(price, name, menuProducts, menuGroup))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴 생성")
        @Test
        void create_menu() {
            MenuPrice price = new MenuPrice(BigDecimal.TEN);
            MenuName name = new MenuName(new FakeMenuPurgomalumClient(), "메뉴 이름");
            MenuProducts menuProducts = new MenuProducts(Arrays.asList(new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, 2L)));
            MenuGroup menuGroup = new MenuGroup("메뉴 그룹 이름", new FakeMenuPurgomalumClient());

            assertAll(
                    () -> assertDoesNotThrow(() -> new Menu(price, name, menuProducts, menuGroup)),
                    () -> assertThat(new Menu(price, name, menuProducts, menuGroup)).isInstanceOf(Menu.class)
            );
        }
    }

    @Nested
    @DisplayName("메뉴 가격 변경")
    class ChangePrice {

        @DisplayName("메뉴에 속한 상품의 총 금액보다 큰 금액으로 메뉴 금액을 변경할 수 없다.")
        @Test
        void change_price_over_menu_products_price() {
            MenuPrice price = new MenuPrice(BigDecimal.TEN);

            Menu menu = menuWithPrice(price);

            assertThatThrownBy(() -> menu.changePrice(new MenuPrice(BigDecimal.valueOf(10_000_000))))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴에 속한 상품의 총 금액보다 작으면서 0이상의 금액으로 변경할 수 있다.")
        @Test
        void change_price() {
            MenuPrice price = new MenuPrice(BigDecimal.TEN);

            Menu menu = menuWithPrice(price);

            assertDoesNotThrow(() -> menu.changePrice(new MenuPrice(BigDecimal.TEN)));
            assertThat(menu.getPrice()).isEqualTo(new MenuPrice(BigDecimal.TEN));
        }
    }

    @Nested
    @DisplayName("메뉴 노출 여부")
    class DisplayedMenu {

        @DisplayName("메뉴를 생성시 메뉴 노출은 비 활성화 상태로 생성된다.")
        @Test
        void menu_displayed_default_is_false() {
            Menu menu = menu();

            assertThat(menu.isDisplayed()).isFalse();
        }

        @DisplayName("메뉴의 노출 여부를 변경할 수 있다.")
        @Test
        void change_displayed() {
            Menu menu = menu();
            menu.display();

            assertThat(menu.isDisplayed()).isTrue();
        }
    }

    private Menu menuWithPrice(MenuPrice price) {
        MenuName name = new MenuName(new FakeMenuPurgomalumClient(), "메뉴 이름");
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, 2L)));
        MenuGroup menuGroup = new MenuGroup("메뉴 그룹 이름", new FakeMenuPurgomalumClient());

        return new Menu(price, name, menuProducts, menuGroup);
    }

    private Menu menu() {
        MenuPrice price = new MenuPrice(BigDecimal.TEN);
        MenuName name = new MenuName(new FakeMenuPurgomalumClient(), "메뉴 이름");
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(new MenuProduct(UUID.randomUUID(), BigDecimal.TEN, 2L)));
        MenuGroup menuGroup = new MenuGroup("메뉴 그룹 이름", new FakeMenuPurgomalumClient());

        return new Menu(price, name, menuProducts, menuGroup);
    }
}
