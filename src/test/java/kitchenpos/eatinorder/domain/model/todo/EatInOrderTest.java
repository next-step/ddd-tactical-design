package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderTest {
    @DisplayName("EeaInOrder를 생성한다.")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final LocalDateTime orderDateTime = LocalDateTime.now();
        final UUID orderTableId = UUID.randomUUID();
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.of(1L, UUID.randomUUID(), 1L, 1L, true);
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(eatInOrderLineItem);

        // when
        final EatInOrder eatInOrder = EatInOrder.create(id, orderDateTime, eatInOrderLineItems, orderTableId);

        // then
        assertAll(
                () -> assertThat(eatInOrder).isNotNull(),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTableId),
                () -> assertThat(eatInOrder.getOrderDateTime()).isEqualTo(orderDateTime),
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.getLineItems()).containsExactly(eatInOrderLineItem)
        );
    }
}