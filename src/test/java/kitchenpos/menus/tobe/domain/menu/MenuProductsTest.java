package kitchenpos.menus.tobe.domain.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

class MenuProductsTest {

    @ParameterizedTest
    @NullAndEmptySource
    void menuProducts_should_exist(List<MenuProduct> menuProducts) {
        Assertions.assertThatThrownBy(() -> new MenuProducts(menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 상품은 필수로 존재해야 합니다.");
    }
}