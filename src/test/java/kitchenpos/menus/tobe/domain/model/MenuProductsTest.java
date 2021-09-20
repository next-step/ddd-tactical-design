package kitchenpos.menus.tobe.domain.model;

import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("MenuProduct들로 MenuProducts를 생성할 수 있다")
    @Test
    void test() {
        assertThat(MENU_PRODUCTS()).isNotNull();
    }
}
