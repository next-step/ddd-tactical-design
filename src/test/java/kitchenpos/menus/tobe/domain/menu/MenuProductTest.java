package kitchenpos.menus.tobe.domain.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.menuProduct;

class MenuProductTest {

    @DisplayName("존재하는 상품만 메뉴 상품으로 등록가능합니다.")
    @Test
    void product_should_exist_When_menuProduct_is_registered() {
        Assertions.assertThatThrownBy(() -> menuProduct())
                .isInstanceOf(IllegalArgumentException.class);
    }

}