package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.vo.Count;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductsTest {
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        menuProducts = List.of(
                new MenuProduct(10L, 2),
                new MenuProduct(11L, 1),
                new MenuProduct(12L, 3)
        );
    }

    @DisplayName("메뉴 상품 목록이 비어있으면 안된다.")
    @Test
    void create() {
        assertThatThrownBy(() -> new MenuProducts(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품 목록을 추가한다.")
    @Test
    void addAll() {
        MenuProducts menuProducts = new MenuProducts(this.menuProducts);

        menuProducts.add(List.of(new MenuProduct(13L, 2), new MenuProduct(14L, 1)));

        assertAll(
                () -> assertThat(menuProducts.getMenuProducts()).hasSize(5),
                () -> assertThat(extractIds(menuProducts)).containsExactly(10L, 11L, 12L, 13L, 14L)
        );
    }

    @DisplayName("메뉴 상품을 추가한다.")
    @Test
    void add() {
        MenuProducts menuProducts = new MenuProducts(this.menuProducts);

        menuProducts.add(13L, 2);

        assertAll(
                () -> assertThat(menuProducts.getMenuProducts()).hasSize(4),
                () -> assertThat(extractIds(menuProducts)).containsExactly(10L, 11L, 12L, 13L)
        );
    }

    @DisplayName("메뉴 상품을 제거한다.")
    @Test
    void remove() {
        MenuProducts menuProducts = new MenuProducts(this.menuProducts);

        menuProducts.remove(11L);

        assertAll(
                () -> assertThat(menuProducts.getMenuProducts()).hasSize(2),
                () -> assertThat(extractIds(menuProducts)).containsExactly(10L, 12L)
        );
    }

    @DisplayName("메뉴 상품의 개수를 변경한다.")
    @Test
    void changeCount() {
        MenuProducts menuProducts = new MenuProducts(this.menuProducts);

        menuProducts.changeCount(11L, 100);

        assertThat(extractCounts(menuProducts)).containsExactly(2, 100, 3);
    }

    private List<Long> extractIds(MenuProducts menuProducts) {
        return menuProducts.getMenuProducts().stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
    }

    private List<Integer> extractCounts(MenuProducts menuProducts) {
        return menuProducts.getMenuProducts().stream()
                .map(MenuProduct::getCount)
                .map(Count::getCount)
                .collect(Collectors.toList());
    }
}
