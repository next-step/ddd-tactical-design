package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.vo.Count;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {
    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new MenuProduct(10L, 3));
    }

    @DisplayName("메뉴 상품을 생성할 때 0개 미만으로 생성할 수 없다.")
    @Test
    void createWithNegative() {
        assertThatThrownBy(() -> new MenuProduct(10L, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품의 개수를 변경한다.")
    @Test
    void changeCount() {
        MenuProduct menuProduct = new MenuProduct(10L, 3);

        menuProduct.changeCount(5);

        assertThat(menuProduct.getCount()).isEqualTo(new Count(5));
    }

    @DisplayName("메뉴 상품의 개수를 0개 미만으로 변경할 수 없다.")
    @Test
    void changeCountWithNegative() {
        MenuProduct menuProduct = new MenuProduct(10L, 3);

        assertThatThrownBy(() -> menuProduct.changeCount(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
