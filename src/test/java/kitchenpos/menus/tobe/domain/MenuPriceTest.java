package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @Test
    @DisplayName("메뉴 가격을 생성할 수 있다.")
    void create() {
        // given
        BigDecimal price = BigDecimal.valueOf(10_000L);

        // when
        MenuPrice actual = new MenuPrice(price);

        // then
        assertThat(actual).isEqualTo(new MenuPrice(price));
    }

    @Test
    @DisplayName("메뉴의 가격은 0원 이상이어야 한다")
    void lessThanZero() {
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-1_000L)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴의 가격은 빈 값이 될 수 없다")
    void emptyValue() {
        assertThatThrownBy(() -> new MenuPrice(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
