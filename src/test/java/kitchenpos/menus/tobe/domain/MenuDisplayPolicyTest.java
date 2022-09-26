package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MenuDisplayPolicyTest {
    private MenuDisplayPolicy menuPriceChangeService;
    private List<Product> products;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menuPriceChangeService = new MenuDisplayPolicy();

        menu = new Menu("후라이드+양념", 40000, 1L,
                List.of(new MenuProduct(10L, 1), new MenuProduct(11L, 1)));

        products = List.of(
                new Product(10L, "후라이드 치킨", 20000),
                new Product(11L, "양념 치킨", 22000)
        );
    }

    @DisplayName("변경된 메뉴의 가격이 메뉴 상품 목록의 가격의 합보다 작으면 숨겨지지 않는다.")
    @Test
    void changePrice() {
        menu.changePrice(39000);

        menuPriceChangeService.decideDisplayed(menu, products);

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("변경된 메뉴의 가격이 메뉴 상품 목록의 가격의 합 이상이면 메뉴가 숨겨진다.")
    @Test
    void changePriceWithWrongPrice() {
        menu.changePrice(44000);

        menuPriceChangeService.decideDisplayed(menu, products);

        assertThat(menu.isDisplayed()).isFalse();
    }
}
