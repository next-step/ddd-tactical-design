package kitchenpos.menus.tobe.domain.model;

import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("productId와 quantity로 menuProduct를 생성할 수 있다")
    @Test
    void menuProduct() {
        assertThat(MENU_PRODUCT1()).isNotNull();
    }

}
