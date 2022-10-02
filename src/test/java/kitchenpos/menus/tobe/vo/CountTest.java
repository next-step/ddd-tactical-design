package kitchenpos.menus.tobe.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CountTest {
    @DisplayName("Count를 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new Count(1));
    }

    @DisplayName("개수는 0개 이상이어야 한다.")
    @Test
    void createWithNegative() {
        assertThatThrownBy(() -> new Count(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
