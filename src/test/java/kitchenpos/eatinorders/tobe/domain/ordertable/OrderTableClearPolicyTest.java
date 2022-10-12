package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 테이블 정리")
class OrderTableClearPolicyTest {

    private UUID menuId1;
    private UUID menuId2;
    private OrderLineItems orderLineItems;
    private UUID orderTableId;
    private OrderTable orderTable;

    private EatInOrderRepository eatInOrderRepository;
    private OrderTableRepository orderTableRepository;

    @BeforeEach
    void setUp() {
        menuId1 = UUID.randomUUID();
        menuId2 = UUID.randomUUID();

        orderLineItems = new OrderLineItems(List.of(
                new OrderLineItem(menuId1, new Quantity(2), new Price(15_000)),
                new OrderLineItem(menuId2, new Quantity(1), new Price(20_000))
        ));

        eatInOrderRepository = new InMemoryOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();

        orderTableId = UUID.randomUUID();
        orderTable = new OrderTable(orderTableId, new DisplayedName("1번"), new NumberOfGuests(2), true);

        orderTableRepository.save(orderTable);
    }

    @DisplayName("매장 주문 완료")
    @Test
    void eatInOrderComplete() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTableId);
        eatInOrder.accepted();
        eatInOrder.served();
        eatInOrder.completed();


        ClearPolicy clearPolicy = new OrderTableClearPolicy(orderTableRepository);
        clearPolicy.clear(orderTableId, eatInOrderRepository);

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(orderTable.isOccupied()).isFalse();
    }
}
