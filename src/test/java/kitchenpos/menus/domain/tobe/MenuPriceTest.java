package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @Test
    @DisplayName("메뉴 가격이 생성 가능하다")
    void constructor() {
        final MenuPrice menuPrice = new MenuPrice(BigDecimal.TEN);
        assertThat(menuPrice).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("메뉴의 가격은 0보다 작을 수 없다")
    @ValueSource(longs = { -1 })
    void constructor_with_negative_value(final Long value) {
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(value)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
