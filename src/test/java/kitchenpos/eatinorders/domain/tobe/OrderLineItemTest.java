package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderLineItemTest {

    @Test
    @DisplayName("주문 항목 생성이 가능하다")
    void constructor() {
        final OrderLineItem expected = new OrderLineItem(1L, BigDecimal.ONE, UUID.randomUUID(),  true, BigDecimal.ONE,1L);
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다")
    @ValueSource(longs = { -1 })
    void constructor_with_negative_Value(final Long quantity) {
        final OrderLineItem expected = new OrderLineItem(1L, BigDecimal.ONE, UUID.randomUUID(), true, BigDecimal.ONE, quantity);
        assertThat(expected).isNotNull();
    }

    @Test
    @DisplayName("숨겨진 메뉴는 주문할 수 없다")
    void constructor_with_hide_menu() {
        assertThatThrownBy(() -> new OrderLineItem(1L, BigDecimal.ONE, UUID.randomUUID(), false, BigDecimal.ONE, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴가 없으면 등록할 수 없다")
    void constructor_with_empty_menu() {
        assertThatThrownBy(() -> new OrderLineItem(1L, BigDecimal.ONE, null, false, BigDecimal.ONE, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 항목과 메뉴의 가격은 일치해야한다")
    void constructor_with_not_matched_price() {
        assertThatThrownBy(() -> new OrderLineItem(1L, BigDecimal.ONE, null, false, BigDecimal.TEN, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("주문 항목의 가격 계산이 가능하다")
    @MethodSource("providePrice")
    void calculate_total_price(final BigDecimal price, final Long quantity, final BigDecimal totalPrice) {
        final OrderLineItem expected = new OrderLineItem(1L, price, UUID.randomUUID(), true, price, quantity);
        assertThat(expected.totalPrice()).isEqualTo(totalPrice);
    }

    private static Stream<Arguments> providePrice() {
        return Stream.of(
            Arguments.of(BigDecimal.valueOf(1L), 5L, BigDecimal.valueOf(5L)),
            Arguments.of(BigDecimal.valueOf(1500L), 5L, BigDecimal.valueOf(7500L)),
            Arguments.of(BigDecimal.valueOf(16_000L), 3L, BigDecimal.valueOf(48_000L))
        );
    }
}
