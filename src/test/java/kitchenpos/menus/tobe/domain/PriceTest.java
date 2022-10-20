package kitchenpos.menus.tobe.domain;

import kitchenpos.common.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- [x] 메뉴의 가격은 0보다 크거나 같아야 한다.
- [x] 메뉴의 가격이 올바르지 않으면 등록할 수 없다. (Null)
 */
class PriceTest {
    @DisplayName("메뉴의 가격은 0보다 크거나 같아야 한다.")
    @Test
    void registerWithLessThanZero() {
        final String INVALID_PRICE_MESSAGE = "가격은 0보다 크거다 같아야 합니다.";
        final BigDecimal price = BigDecimal.valueOf(-1L);

        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PRICE_MESSAGE);
    }

    @DisplayName("메뉴 가격은 비어있을 수 없다.")
    @NullSource
    @ParameterizedTest
    void registerWithEmptyName(final BigDecimal price) {
        final String EMPTY_NAME_MESSAGE = "가격은 비어있을 수 없습니다.";

        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE);
    }
}
