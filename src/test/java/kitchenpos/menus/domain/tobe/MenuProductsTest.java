package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.support.MenuGenerator.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성할 수 있다.")
    @Test
    void create() {
        List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 2l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 2l, BigDecimal.valueOf(10000)));

        final MenuProducts actual = new MenuProducts(givenMenuProducts);

        assertThat(actual).isNotNull();
    }

    @DisplayName("메뉴 상품 목록의 합을 리턴한다.")
    @Test
    void total() {
        List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(10000)),
                createMenuProduct(2L, 2l, BigDecimal.valueOf(10000)));
        final MenuProducts menuProducts = new MenuProducts(givenMenuProducts);

        final MenuPrice actual = menuProducts.getTotalPrice();

        assertThat(actual).isEqualTo(new MenuPrice(BigDecimal.valueOf(30000)));
    }
}
