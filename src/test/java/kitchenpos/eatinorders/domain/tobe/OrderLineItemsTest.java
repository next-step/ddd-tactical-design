package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderLineItemsTest {

    @Test
    @DisplayName("주문 항목 묶음 생성이 가능하다")
    void constructor() {
        final OrderLineItems expected = new OrderLineItems(List.of(new OrderLineItem(1L, BigDecimal.ONE, UUID.randomUUID(), true, BigDecimal.ONE, 1L)));
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("주문 항목은 필수이다")
    @NullAndEmptySource
    void constructor_with_null_and_empty_value(final List<OrderLineItem> orderLineItems) {
        assertThatThrownBy(() -> new OrderLineItems(orderLineItems))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("주문 항목의 총 가격 계산이 가능하다")
    @MethodSource("provideOrderLineItemsAndTotalPrice")
    void calculate_total_price(final List<OrderLineItem> orderLineItems, final BigDecimal totalPrice) {
        final OrderLineItems expected = new OrderLineItems(orderLineItems);
        assertThat(expected.totalPrice()).isEqualTo(totalPrice);
    }

    private static Stream<Arguments> provideOrderLineItemsAndTotalPrice() {
        return Stream.of(
            Arguments.of(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), true, BigDecimal.TEN, 5L)), BigDecimal.valueOf(50L)),
            Arguments.of(List.of(new OrderLineItem(1L, BigDecimal.TEN, UUID.randomUUID(), true, BigDecimal.TEN, 5L), new OrderLineItem(2L, BigDecimal.valueOf(15_000L), UUID.randomUUID(), true, BigDecimal.valueOf(15_000L), 3L)), BigDecimal.valueOf(45_050L))
        );
    }
}
