package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EatInOrderTest {

    private OrderLineItems orderLineItems;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        this.orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 10_000)));
        this.orderTable = new OrderTable("9번");
    }

    @Test
    @DisplayName("성공")
    void success() {
        //given when
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());

        //then
        assertThat(eatInOrder.getId()).isNotNull();
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTable.getId());
    }

    @Test
    @DisplayName("orderLineItem이 비어있으면 안된다.")
    void mustHaveOneOrderLineItem() {
        assertThatThrownBy(() -> new EatInOrder(new OrderLineItems(new ArrayList<>()), orderTable.getId()))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1개 이상의 주문항목이 필요");

    }

}
