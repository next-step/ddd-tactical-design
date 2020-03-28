package kitchenpos.menus.tobe.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴제품리스트를 생성할 수 있다.")
    @Test
    void create() {
        // given
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        final MenuProducts result = new MenuProducts(menuProducts);

        // then
        assertThat(result.get()).containsExactlyInAnyOrderElementsOf(menuProducts);
    }

    @DisplayName("메뉴제품리스트 생성 시, 제품이 중복될 수 없다.")
    @Test
    void createFailsWhenProductsDuplicated() {
        // given
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(1L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuProducts(menuProducts);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴제품의 가격 총합을 계산할 수 있다.")
    @Test
    void getTotalPrice() {
        // given
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );
        final MenuProducts result = new MenuProducts(menuProducts);

        // when
        final BigDecimal totalPrice = result.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(BigDecimal.valueOf(5000));
    }
}
