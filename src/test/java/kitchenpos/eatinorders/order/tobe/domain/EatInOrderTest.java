package kitchenpos.eatinorders.order.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderTest {

    @DisplayName("매장주문을 생성한다.")
    @Test
    void create() {
        final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
        final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
        final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);

        final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);

        assertAll(
                () -> assertThat(eatInOrder.id()).isNotNull(),
                () -> assertThat(eatInOrder.orderTableId()).isNotNull(),
                () -> assertThat(eatInOrder.orderDateTime()).isNotNull(),
                () -> assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.eatInOrderLineItems()).isNotNull()
        );
    }
}
