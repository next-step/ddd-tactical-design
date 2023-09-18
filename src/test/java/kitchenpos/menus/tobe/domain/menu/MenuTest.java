package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    @DisplayName("Menu를 생성할 수 있다.")
    @Test
    void create() {
        MenuProduct menuProduct = menuProduct();
        Menu menu = menu(30000L, true, menuProduct);
        assertAll(
                () -> assertThat(menu.getId()).isNotNull(),
                () -> assertThat(menu.getDisplayedName()).isEqualTo(MenuDisplayedName.from("후라이드+후라이드", menuDisplayedNamePolicy())),
                () -> assertThat(menu.getPrice()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(30000L))),
                () -> assertThat(menu.isDisplayed()).isTrue(),
                () -> assertThat(menu.getMenuGroup().getName()).isEqualTo(menuGroup().getName()),
                () -> assertThat(menu.getMenuProducts()).isEqualTo(new MenuProducts(List.of(menuProduct)))
        );
    }

    @DisplayName("Menu를 생성할 때 Menu의 가격이 Menu Product의 총 가격보다 크다면 예외를 던진다.")
    @Test
    void createThrowException() {
        Product product = product("후라이드", 15000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        assertThatThrownBy(() -> menu(30001L, true, menuProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("Menu Product의 가격을 변경할 수 있다.")
    @Test
    void changeMenuProductPrice() {
        Product product = product("후라이드", 16000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        Menu menu = menu(30000L, menuProduct);
        menu.changeMenuProductPrice(menuProduct.getProductId(), BigDecimal.valueOf(15000L));
        assertThat(menuProduct.getPrice()).isEqualTo(BigDecimal.valueOf(15000L));
    }

    @DisplayName("Menu Product의 가격을 변경한 후 Menu의 가격이 Menu Product의 총 가격보다 커지면 Menu를 숨겨진 메뉴로 변경한다.")
    @Test
    void changeMenuProductPriceWhenMenuPriceIsLessThanMenuProductsTotalAmount() {
        Product product = product("후라이드", 16000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        Menu menu = menu(30001L, true, menuProduct);
        menu.changeMenuProductPrice(menuProduct.getProductId(), BigDecimal.valueOf(15000L));
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("Menu의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Menu menu = menu(30000L, true, menuProduct());
        menu.changePrice(MenuPrice.from(BigDecimal.valueOf(20000L)));
        assertThat(menu.getPrice()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(20000L)));
    }

    @DisplayName("Menu의 가격을 변경할 때 Menu의 가격이 Menu Product의 총 가격보다 커진다면 예외를 던진다.")
    @Test
    void changePriceThrowException() {
        Product product = product("후라이드", 16000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        Menu menu = menu(30001L, true, menuProduct);
        assertThatThrownBy(() -> menu.changePrice(MenuPrice.from(BigDecimal.valueOf(32001L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Menu를 display 상태로 변경할 수 있다.")
    @Test
    void display() {
        Menu menu = menu(30000L, false, menuProduct());
        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("Menu를 display 상태로 변경할 때 Menu의 가격이 Menu Product의 총 가격보다 크다면 예외를 던진다.")
    @Test
    void displayThrowException() {
        Product product = product("후라이드", 16000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        Menu menu = menu(30001L, false, menuProduct);
        menu.changeMenuProductPrice(menuProduct.getProductId(), BigDecimal.valueOf(15000L));
        assertThatThrownBy(menu::display)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("Menu를 숨김 상태로 변경할 수 있다.")
    @Test
    void hide() {
        Menu menu = menu(30000L, true, menuProduct());
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("Menu의 가격을 Menu Product의 총 가격과 Menu Product의 총 수량을 곱한 값으로 계산할 수 있다.")
    @Test
    void priceMultiplyByQuantity() {
        Product product = product("후라이드", 16000L);
        MenuProduct menuProduct = menuProduct(product, 2L);
        Menu menu = menu(30000L, true, menuProduct);
        assertThat(menu.priceMultiplyByQuantity(2L)).isEqualTo(BigDecimal.valueOf(60000L));
    }
}
