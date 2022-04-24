package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.support.MenuGenerator.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성할 수 있다.")
    @Test
    void create() {
        List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 2l),
                createMenuProduct(2L, 2l));

        final MenuProducts actual = new MenuProducts(givenMenuProducts);

        assertThat(actual).isNotNull();
    }
}
