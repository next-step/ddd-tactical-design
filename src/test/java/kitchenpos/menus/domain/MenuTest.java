package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    @Test
    @DisplayName("메뉴 가격이 메뉴상품 총합가격보다 적거나 같으면 메뉴 노출")
    void testValidate_priceLessThanEqualTotal_returnsTrue() {
        // arrange
        Menu menu = new Menu();
        menu.setPrice(new BigDecimal(64_000));
        List<MenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(menuProduct());
        menuProducts.add(menuProduct());
        menu.setMenuProducts(menuProducts);

        // act
        menu.validate();

        // assert
        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴 가격이 메뉴상품 총합가격보다 크면 메뉴 숨김")
    void testValidate_price_greaterThan_returnsFalse() {
        // arrange
        Menu menu = new Menu();
        menu.setPrice(new BigDecimal(64_001));
        List<MenuProduct> menuProducts = new ArrayList<>();
        menuProducts.add(menuProduct());
        menuProducts.add(menuProduct());
        menu.setMenuProducts(menuProducts);

        // act
        menu.validate();

        // assert
        assertThat(menu.isDisplayed()).isFalse();
    }
}