package kitchenpos.menus.tobe.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @DisplayName("가격을 생성한다.")
    @ValueSource(ints = {0, 10000})
    @ParameterizedTest
    void create(int price) {
        assertDoesNotThrow(() -> new Price(price));
    }

    @DisplayName("가격은 음수일 수 없다.")
    @Test
    void createWithNegative() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
