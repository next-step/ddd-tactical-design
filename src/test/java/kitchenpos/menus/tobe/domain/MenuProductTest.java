package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {

    @Test
    @DisplayName("개수는 0개 이상이어야 한다.")
    void canNotHaveMinusQuantity(){
        assertThatThrownBy( () -> new MenuProduct(UUID.randomUUID(), 18000, -1))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
