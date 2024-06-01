package kitchenpos.menus.domain.tobe;

import kitchenpos.products.domain.tobe.FakeProfanities;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    Product product;
    MenuProducts menuProducts;
    MenuGroup menuGroup;
    BigDecimal price;

    @BeforeEach
    void setUp() {
        product = Product.createProduct("후라이드", BigDecimal.valueOf(16000), new FakeProfanities());
        menuGroup = new MenuGroup("세트메뉴");
        menuProducts = new MenuProducts(Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2)));
        price = BigDecimal.valueOf(19000);
    }

    @Test
    void create(){
        final Menu actual = Menu.createMenu("후라이드+후라이드", menuGroup, price, menuProducts, new FakeProfanities());

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getDisplayedName().getName()).isEqualTo("후라이드+후라이드"),
                () -> assertThat(actual.getPrice().getPriceValue()).isEqualTo(price),
                () -> assertThat(actual.getMenuGroup().getId()).isEqualTo(menuGroup.getId()),
                () -> assertThat(actual.isDisplayed()).isEqualTo(true),
                () -> assertThat(actual.getMenuProducts().getMenuProducts()).hasSize(1)
        );
    }

    @Test
    void changePrice(){
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, price, menuProducts, new FakeProfanities());
        BigDecimal changePrice = BigDecimal.valueOf(10000);

        menu.changePrice(Price.createPrice(changePrice));

        assertThat(menu.getPrice().getPriceValue()).isEqualTo(changePrice);
    }
}
