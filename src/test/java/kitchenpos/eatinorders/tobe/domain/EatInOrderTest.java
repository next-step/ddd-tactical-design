package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EatInOrderTest {

    private OrderLineItems orderLineItems;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        this.orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 10000))) ;
        this.orderTable = new OrderTable("9번");
    }

    @Test
    @DisplayName("성공")
    void success() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);

        assertThat(eatInOrder.getId()).isNotNull();
        assertThat(eatInOrder.getType()).isEqualTo(OrderType.EAT_IN);
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(eatInOrder.getOrderTable()).isEqualTo(orderTable);
    }

}