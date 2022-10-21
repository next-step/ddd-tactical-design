package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineItemsTest {

    private List<OrderLineItem> orderLineItems;

    @BeforeEach
    void setUp() {
        OrderLineItem orderLineItem1 = new OrderLineItem(1L, 10L, BigDecimal.valueOf(20000), 2);
        OrderLineItem orderLineItem2 = new OrderLineItem(2L, 11L, BigDecimal.valueOf(19000), 1);
        orderLineItems = List.of(orderLineItem1, orderLineItem2);
    }

    @DisplayName("주문 메뉴 목록을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new OrderLineItems(orderLineItems));
    }

    @DisplayName("주문 메뉴는 하나 이상이어야 한다.")
    @Test
    void createWithEmptyList() {
        assertThatThrownBy(() -> new OrderLineItems(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 메뉴의 수량을 변경한다.")
    @Test
    void changeCount() {
        OrderLineItems orderLineItems = new OrderLineItems(this.orderLineItems);

        orderLineItems.changeQuantity(2L, 10);

        List<Integer> quantities = orderLineItems.getOrderLineItems().stream()
                .map(OrderLineItem::getQuantity)
                .collect(Collectors.toList());
        assertThat(quantities).containsExactly(2, 10);
    }
}
