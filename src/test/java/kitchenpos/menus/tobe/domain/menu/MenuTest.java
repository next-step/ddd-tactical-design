package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.domain.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴")
class MenuTest {

    @DisplayName("상품이 없으면 등록 할 수 없다.")
    @Test
    void createMenu() {
        List<MenuProduct> menuProducts = new ArrayList<>();
        assertThatThrownBy(() -> new Menu(menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품을 등록해주세요.");
    }
}
