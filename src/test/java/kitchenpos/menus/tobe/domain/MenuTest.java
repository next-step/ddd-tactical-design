package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.vo.Count;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        menuProducts = List.of(
                new MenuProduct(10L, 2),
                new MenuProduct(11L, 1),
                new MenuProduct(12L, 3)
        );
    }

    @DisplayName("메뉴를 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new Menu("신메뉴", 50000, 5L, menuProducts));
    }

    @DisplayName("빈 메뉴 상품 목록으로 메뉴를 생성할 수 없다.")
    @Test
    void createWithEmptyMenuProducts() {
        assertThatThrownBy(() -> new Menu("신메뉴", 50000, 5L, Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 상품을 추가한다.")
    @Test
    void addProduct() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);

        menu.addProduct(13L, 1);

        assertAll(
                () -> assertThat(menu.getMenuProducts()).hasSize(4),
                () -> assertThat(extractIds(menu.getMenuProducts())).containsExactly(10L, 11L, 12L, 13L)
        );
    }

    @DisplayName("메뉴에서 상품을 제거한다.")
    @Test
    void removeProduct() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);

        menu.removeProduct(11L);

        assertAll(
                () -> assertThat(menu.getMenuProducts()).hasSize(2),
                () -> assertThat(extractIds(menu.getMenuProducts())).containsExactly(10L, 12L)
        );
    }

    @DisplayName("메뉴의 가격을 변경한다.")
    @Test
    void changePrice() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);

        menu.changePrice(40000);

        assertThat(menu.getPrice()).isEqualTo(BigDecimal.valueOf(40000));
    }

    @DisplayName("메뉴 상품의 개수를 변경한다.")
    @Test
    void changeProductCount() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);

        menu.changeProductCount(11L, 100);

        assertAll(
                () -> assertThat(menu.getMenuProducts()).hasSize(3),
                () -> assertThat(extractCounts(menu.getMenuProducts())).containsExactly(2, 100, 3)
        );
    }

    @DisplayName("메뉴를 공개한다.")
    @Test
    void display() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);
        menu.hide();

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨긴다.")
    @Test
    void hide() {
        Menu menu = new Menu("신메뉴", 50000, 5L, menuProducts);

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    private List<Long> extractIds(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    private List<Integer> extractCounts(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(MenuProduct::getCount)
                .map(Count::getCount)
                .collect(Collectors.toList());
    }
}
