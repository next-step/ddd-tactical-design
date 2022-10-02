package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.UUID;
import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {
    @DisplayName("매장 주문 생성 검증")
    @Test
    void create() {
        // given
        UUID id = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.WAITING;
        LocalDateTime orderDateTime = LocalDateTime.now();
        OrderLineItems orderLineItems = new OrderLineItems(TobeFixtures.orderLineItem());
        String orderTableId = UUID.randomUUID().toString();

        // when
        EatInOrder eatInOrder = new EatInOrder(id, orderStatus, orderDateTime,
            orderLineItems, orderTableId);

        // then
        assertAll(
            () -> assertThat(eatInOrder.getId()).isNotNull(),
            () -> assertThat(eatInOrder.getStatus()).isEqualTo(orderStatus),
            () -> assertThat(eatInOrder.getOrderDateTime()).isEqualTo(orderDateTime),
            () -> assertThat(eatInOrder.getOrderLineItems()).isNotNull(),
            () -> assertThat(eatInOrder.getOrderTableId()).isNotNull()
        );
    }
}
