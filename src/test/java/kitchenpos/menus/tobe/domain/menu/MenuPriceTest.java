package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @DisplayName("메뉴 가격은 0원 이하일 수 없다.")
    @Test
    void createMenuPrice() {
        assertThatThrownBy(() -> new MenuPrice(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 가격은 0원 이하일 수 없습니다.");
    }
}
