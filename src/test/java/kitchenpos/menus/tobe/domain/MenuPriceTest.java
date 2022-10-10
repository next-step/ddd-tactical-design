package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- [x] 메뉴의 가격은 0보다 크거나 같아야 한다.
- 메뉴의 가격이 올바르지 않으면 등록할 수 없다. (Null, Empty)
 */
class MenuPriceTest {
    @DisplayName("메뉴의 가격은 0보다 크거나 같아야 한다.")
    @Test
    void registerWithLessThanZero() {
        final String INVALID_PRICE_MESSAGE = "메뉴 가격은 0보다 크거다 같아야 합니다.";
        final BigDecimal price = BigDecimal.valueOf(-1L);

        assertThatThrownBy(() -> new MenuPrice(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PRICE_MESSAGE);
    }
}
