package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


class EatInOrderTest {

    private OrderLineItems orderLineItems;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        this.orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 10000)));
        this.orderTable = new OrderTable("9번");
    }

    @Test
    @DisplayName("성공")
    void success() {
        //given when
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable);

        //then
        assertThat(eatInOrder.getId()).isNotNull();
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(eatInOrder.getOrderTable()).isEqualTo(orderTable);
    }

    @Test
    @DisplayName("orderLineItem이 비어있으면 안된다.")
    void mustHaveOneOrderLineItem() {
        assertThatThrownBy(() -> new EatInOrder(new OrderLineItems(new ArrayList<>()), orderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("테이블은 비어 있는 테이블만 매장 주문이 가능하다.")
    void onlyOrderWhenTableClear() {
        //given when
        orderTable.sit();
        orderTable.changeNumberOfGuests(1);

        //then
        assertThatThrownBy(() -> new EatInOrder(orderLineItems, orderTable))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
