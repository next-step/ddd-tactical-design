package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProductsTest {
    @Test
    void MenuProducts는_MenuProduct를_1개_이상_갖는다() {
        assertDoesNotThrow(() -> new MenuProducts(List.of(new MenuProduct(UUID.randomUUID(), new Quantity(0), new Price(1000)))));
    }

    @Test
    void MenuProducts는_MenuProduct가_0개일_수_없다() {
        assertThatThrownBy(() -> new MenuProducts(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴에 속한 상품은 1개 이상이어야 합니다. 현재 값: 0");
    }

    @Test
    void MenuProducts_동등성_비교() {
        List<MenuProduct> menuProducts = List.of(new MenuProduct(UUID.randomUUID(), new Quantity(0), new Price(1000)));
        List<MenuProduct> otherMenuProducts = List.of(new MenuProduct(UUID.randomUUID(), new Quantity(0), new Price(1000)));

        MenuProducts actual = new MenuProducts(menuProducts);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals(5)).isFalse();

        assertThat(actual.equals(new MenuProducts(menuProducts))).isTrue();
        assertThat(actual.equals(new MenuProducts(otherMenuProducts))).isFalse();
    }

    @Test
    void MenuProducts_hashCode() {
        List<MenuProduct> menuProducts = List.of(new MenuProduct(UUID.randomUUID(), new Quantity(0), new Price(1000)));
        List<MenuProduct> otherMenuProducts = List.of(new MenuProduct(UUID.randomUUID(), new Quantity(0), new Price(1000)));
        
        MenuProducts actual = new MenuProducts(menuProducts);

        assertThat(actual.hashCode()).isEqualTo(new MenuProducts(menuProducts).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new MenuProducts(otherMenuProducts).hashCode());
    }
}
