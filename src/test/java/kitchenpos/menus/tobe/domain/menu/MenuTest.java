package kitchenpos.menus.tobe.domain.menu;


import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴")
class MenuTest {

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 8000, 후라이드상품, false"})
    void existProduct(String menuGroupName, String menuName, BigDecimal menuPrice, boolean isProfanity) {
        MenuProducts menuProducts = new MenuProducts();
        assertThatThrownBy(() -> menu(menuGroupName, menuName, menuPrice, isProfanity, menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품을 등록해주세요.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 10000, 후라이드상품, false, 8000, 1"})
    void sumMenuProducts(String menuGroupName, String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity) {
        assertThatThrownBy(() -> menu(menuGroupName, menuName, menuPrice, isProfanity, menuProducts(displayedName, isProfanity, productPrice, quantity)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 8000, 후라이드상품, false, 8000, 1"})
    void hideMenu(String menuGroupName, String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity) {
        Menu menu = menu(menuGroupName, menuName, menuPrice, false, menuProducts(displayedName, isProfanity, productPrice, quantity));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @ParameterizedTest
    @CsvSource({"후라이드메뉴, 8000, 후라이드상품, false, 8000, 1"})
    void checkMenuGroup(String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity) {
        assertThatThrownBy(() -> new Menu(new Name(menuName, isProfanity), new Price(menuPrice), menuProducts(displayedName, isProfanity, productPrice, quantity), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴는 특정 메뉴 그룹에 속해야 한다.");
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 8000, 후라이드상품, false, 8000, 1, 7000"})
    void changePrice(String menuGroupName, String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity, BigDecimal changeMenuPrice) {
        Menu menu = menu(menuGroupName, menuName, menuPrice, false, menuProducts(displayedName, isProfanity, productPrice, quantity));
        menu.changePrice(new Price(changeMenuPrice));
        assertThat(menu.price()).isEqualTo(new Price(changeMenuPrice));
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 8000, 후라이드상품, false, 8000, 1"})
    void displayMenu(String menuGroupName, String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity) {
        Menu menu = menu(menuGroupName, menuName, menuPrice, false, menuProducts(displayedName, isProfanity, productPrice, quantity));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @ParameterizedTest
    @CsvSource({"후라이드그룹, 후라이드메뉴, 8000, 후라이드상품, false, 8000, 1, 9000"})
    void displayLimitPrice(String menuGroupName, String menuName, BigDecimal menuPrice, String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity, BigDecimal changeMenuPrice) {
        Menu menu = menu(menuGroupName, menuName, menuPrice, isProfanity, menuProducts(displayedName, isProfanity, productPrice, quantity));
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
        assertThatThrownBy(() -> menu.changePrice(new Price(changeMenuPrice)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
    }

    private static MenuProducts menuProducts(String displayedName, boolean isProfanity, BigDecimal productPrice, BigDecimal quantity) {
        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(menuProduct(displayedName, isProfanity, quantity, productPrice));
        return menuProducts;
    }

    private static MenuProduct menuProduct(String displayedName, boolean isProfanity, BigDecimal quantity, BigDecimal productPrice) {
        return new MenuProduct(new Product(UUID.randomUUID(), new Name(displayedName, isProfanity),
                new Price(productPrice)), new Quantity(quantity));
    }


    private static Menu menu(String menuGroupName, String menuName, BigDecimal menuPrice, boolean isProfanity, MenuProducts menuProducts) {
        return new Menu(new Name(menuName, isProfanity), new Price(menuPrice), menuProducts, new MenuGroup(UUID.randomUUID(), new MenuGroupName(menuGroupName)));
    }

}
