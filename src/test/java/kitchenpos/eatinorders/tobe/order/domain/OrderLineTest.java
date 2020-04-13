package kitchenpos.eatinorders.tobe.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineTest {

    @DisplayName("주문 라인을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final Long menuId = 2L;
        final long quantity = 3L;

        // when
        final OrderLine orderLine = new OrderLine(menuId, quantity);

        // then
        assertThat(orderLine.getMenuId()).isEqualTo(menuId);
        assertThat(orderLine.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("주문 라인 생성 시, 메뉴가 입력되어야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1, 0})
    void createFailsWhenMenuIdInvalid(final Long menuId) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new OrderLine(menuId, 1L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 라인 생성 시, 수량을 1개 이상 입력해야한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void createFailsWhenQuantityLessThan1(final long quantity) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new OrderLine(1L, quantity);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
