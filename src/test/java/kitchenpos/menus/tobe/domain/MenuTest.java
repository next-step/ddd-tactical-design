package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.domain.MenuFixtures.menu;
import static kitchenpos.menus.tobe.domain.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.exception.MaximumMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MenuTest {

    @DisplayName("메뉴 생성")
    @Nested
    class CreateTest {

        @DisplayName("메뉴를 생성할 수 있다.")
        @Test
        void create() {
            String menuName = "후라이드+후라이드";
            String groupName = "두마리 치킨";
            BigDecimal price = BigDecimal.ZERO;
            boolean displayed = true;
            MenuProduct menuProduct = menuProduct(1, BigDecimal.ONE);

            assertThatCode(() -> menu(
                    menuName,
                    groupName,
                    price,
                    displayed,
                    menuProduct
            )).doesNotThrowAnyException();
        }


        @DisplayName("메뉴의 가격은 메뉴 상품 가격보다 크게 설정할 수 없다.")
        @Test
        void error() {
            BigDecimal requestPrice = BigDecimal.ONE;

            assertThatExceptionOfType(MaximumMenuPriceException.class)
                    .isThrownBy(() -> menu(
                            requestPrice,
                            menuProduct(1, BigDecimal.ZERO)
                    ));
        }
    }

    @DisplayName("메뉴 가격 변경")
    @Nested
    class ChangePriceTest {

        @DisplayName("가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            Menu menu = createMenu();
            MenuPrice menuPrice = new MenuPrice(BigDecimal.ONE);
            menu.changePrice(menuPrice);

            assertThat(menu.getPrice()).isEqualTo(menuPrice);
        }

        @DisplayName("메뉴의 가격이 메뉴 상품 가격보다 설정할 수 없다.")
        @Test
        void error() {
            BigDecimal menuPrice = BigDecimal.ONE;
            BigDecimal menuProductAmount = BigDecimal.ONE;
            Menu menu = menu(menuPrice, menuProductAmount);

            assertThatExceptionOfType(MaximumMenuPriceException.class)
                    .isThrownBy(() -> {
                        MenuPrice requestMenuPrice = new MenuPrice(BigDecimal.TEN);
                        menu.changePrice(requestMenuPrice);
                    });
        }
    }

    @DisplayName("전시 메뉴 설정")
    @Nested
    class DisplayedMenu {

        @DisplayName("메뉴는 전시 메뉴 또는 숨겨진 메뉴로 설정할 수 있다.")
        @Test
        void displayAndHidden() {
            Menu menu = createMenu();

            menu.display();
            assertThat(menu.isDisplayed()).isTrue();

            menu.hidden();
            assertThat(menu.isDisplayed()).isFalse();
        }

        @DisplayName("전시 메뉴의 가격이 메뉴 상품 가격보다 크게 설정할 수 없다.")
        @Test
        void displayedMenu() {
            BigDecimal menuPrice = BigDecimal.TEN;
            BigDecimal menuProductAmount = BigDecimal.ONE;
            Menu menu = menu(menuPrice, menuProductAmount);

            assertThatExceptionOfType(MaximumMenuPriceException.class)
                    .isThrownBy(() -> menu.display());
        }
    }

    private Menu createMenu() {
        BigDecimal menuPrice = BigDecimal.ZERO;
        MenuProduct menuProduct = menuProduct(1, BigDecimal.ONE);
        return menu(menuPrice, menuProduct);
    }
}
