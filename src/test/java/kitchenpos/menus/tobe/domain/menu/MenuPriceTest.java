package kitchenpos.menus.tobe.domain.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuPriceTest {

    @DisplayName("메뉴의 가격은 필수로 존재해야합니다.")
    @Test
    void price_should_exist() {
        Assertions.assertThatThrownBy(() -> new MenuPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}