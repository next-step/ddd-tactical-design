package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static kitchenpos.menus.tobe.MenuFixtures.menuGroup;
import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static kitchenpos.menus.tobe.MenuFixtures.menuProducts;
import static kitchenpos.menus.tobe.MenuFixtures.price;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.global.exception.MaximumPriceException;
import kitchenpos.global.vo.Price;
import kitchenpos.menus.tobe.MenuFixtures;
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
            long price = 15_000;
            boolean displayed = true;
            MenuProduct menuProduct = menuProduct(1, 16_000);

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
            long requestPrice = 1;

            assertThatExceptionOfType(MaximumPriceException.class)
                    .isThrownBy(() -> menu(
                            requestPrice,
                            menuProduct(1, 0)
                    ));
        }
    }

    @DisplayName("메뉴 가격 변경")
    @Nested
    class ChangePriceTest {

        @DisplayName("가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            Menu menu = menu("후라이드+후라이드");
            Price Price = new Price(BigDecimal.ONE);
            menu.changePrice(Price);

            assertThat(menu.getPrice()).isEqualTo(Price);
        }

        @DisplayName("메뉴의 가격이 메뉴 상품 가격보다 설정할 수 없다.")
        @Test
        void error() {
            MenuProduct menuProduct = menuProduct(1, 1_000);
            Menu menu = new Menu(
                    UUID.randomUUID(),
                    MenuFixtures.name(" 후라이드+후라이드"),
                    price(0),
                    menuGroup("두마리 치킨"),
                    false,
                    menuProducts(menuProduct)
            );

            assertThatExceptionOfType(MaximumPriceException.class)
                    .isThrownBy(() -> {
                        Price requestPrice = menuProduct.getPrice().add(price(1));
                        menu.changePrice(requestPrice);
                    });
        }
    }

    @DisplayName("전시 메뉴 설정")
    @Nested
    class DisplayedMenu {

        @DisplayName("메뉴는 전시 메뉴 또는 숨겨진 메뉴로 설정할 수 있다.")
        @Test
        void displayAndHidden() {
            Menu menu = menu("후라이드+후라이드");

            menu.display();
            assertThat(menu.isDisplayed()).isTrue();

            menu.hidden();
            assertThat(menu.isDisplayed()).isFalse();
        }

        @DisplayName("전시 메뉴의 가격이 메뉴 상품 가격보다 크게 설정할 수 없다.")
        @Test
        void displayedMenu() {
            long price = 1;
            long menuProductPrice = 0;

            MenuProduct menuProduct = menuProduct(1, menuProductPrice);
            Menu menu = new Menu(
                    UUID.randomUUID(),
                    MenuFixtures.name(" 후라이드+후라이드"),
                    price(price),
                    menuGroup("두마리 치킨"),
                    false,
                    menuProducts(menuProduct)
            );

            assertThatExceptionOfType(MaximumPriceException.class)
                    .isThrownBy(menu::display);
        }
    }
}
