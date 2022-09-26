package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuPricePolicyTest {
    private MenuPricePolicy menuPriceChangeService;
    private List<Product> products;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menuPriceChangeService = new MenuPricePolicy();

        menu = new Menu("후라이드+양념", 40000, 1L,
                List.of(new MenuProduct(10L, 1), new MenuProduct(11L, 1)));

        products = List.of(
                new Product(10L, "후라이드 치킨", 20000),
                new Product(11L, "양념 치킨", 22000)
        );
    }

    @DisplayName("메뉴의 가격을 변경한다.")
    @Test
    void changePrice() {
        menuPriceChangeService.changePrice(menu, products, 39000);

        assertAll(
                () -> assertThat(menu.isDisplayed()).isTrue(),
                () -> assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(39000))
        );
    }

    @DisplayName("변경된 메뉴의 가격이 메뉴 상품 목록의 가격의 합 이상이면 메뉴가 숨겨진다.")
    @Test
    void changePriceWithWrongPrice() {
        menuPriceChangeService.changePrice(menu, products, 44000);

        assertAll(
                () -> assertThat(menu.isDisplayed()).isFalse(),
                () -> assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(44000))
        );
    }
}
