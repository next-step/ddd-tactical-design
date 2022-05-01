package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.tobe.domain.vo.TableEmptyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDateTime;

import static kitchenpos.Fixtures.orderLineItems;
import static kitchenpos.Fixtures.tobeOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {

    @DisplayName(value = "매장 식사 주문을 생성한다")
    @Test
    void create() throws Exception {
        //given
        OrderLineItems orderLineItems = orderLineItems();
        TobeOrderTable table = tobeOrderTable("1번", 2, TableEmptyStatus.OCCUPIED);
        //when
        EatInOrder order = EatInOrder.Of(orderLineItems, table);
        //then
        assertThat(order).isNotNull();
        assertAll(
                () -> assertThat(order.getStatus().isWaiting()).isTrue(),
                () -> assertThat(order.getOrderDateTime()).isBeforeOrEqualTo(LocalDateTime.now()),
                () -> assertThat(order.getOrderLineItems().size()).isOne(),
                () -> assertThat(order.getOrderTable().getName().getValue()).isEqualTo("1번"),
                () -> assertThat(order.getOrderTable().isEmpty()).isFalse()
        );
    }

    @DisplayName(value = "주문테이블이 없다면, 매장식사 주문을 생성할 수 없다")
    @NullSource
    @ParameterizedTest
    void create_fail_orderTable(final TobeOrderTable table) throws Exception {
        //given
        OrderLineItems orderLineItems = orderLineItems();
        //when&&then
        assertThatThrownBy(() -> EatInOrder.Of(orderLineItems, table))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName(value = "주문테이블이 비어있다면, 매장식사 주문을 생성할 수 없다")
    @Test
    void create_fail_orderTable_empty() throws Exception {
        //given
        final OrderLineItems orderLineItems = orderLineItems();
        final TobeOrderTable table = tobeOrderTable("1번", 0, TableEmptyStatus.EMPTY);
        //when&&then
        assertThatThrownBy(() -> EatInOrder.Of(orderLineItems, table))
                .isInstanceOf(IllegalStateException.class);
    }
}
