package kitchenpos.eatinorders.tobe.domain.eatinorder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.shared.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

    @DisplayName("매장 식사를 생성할 수 있다.")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final OrderType orderType = OrderType.EAT_IN;
        final EatInOrderStatus orderStatus = EatInOrderStatus.WAITING;
        final LocalDateTime orderDateTime = LocalDateTime.now();
        final List<EatInOrderLineItem> eatInOrderLineItems = List.of(
            new EatInOrderLineItem(1L, UUID.randomUUID(), 1));
        final UUID restaurantTableId = UUID.randomUUID();

        // when
        EatInOrder eatInOrder = new EatInOrder(id, orderType, orderStatus, orderDateTime,
            eatInOrderLineItems, restaurantTableId);

        // then
        assertThat(eatInOrder.getId()).isEqualTo(id);
        assertThat(eatInOrder.getType()).isEqualTo(orderType);
        assertThat(eatInOrder.getStatus()).isEqualTo(orderStatus);
        assertThat(eatInOrder.getOrderDateTime()).isEqualTo(orderDateTime);
        assertThat(eatInOrder.getOrderLineItems()).isEqualTo(eatInOrderLineItems);
        assertThat(eatInOrder.getRestaurantTableId()).isEqualTo(restaurantTableId);
    }
}
