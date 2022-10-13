package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableEmptyTablePolicy;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderFactoryTest {

    private OrderTableRepository orderTableRepository;

    private UUID menuId1;
    private UUID menuId2;
    private OrderLineItems orderLineItems;
    private UUID orderTableId;
    private UUID orderTableEmptyId;
    private OrderTable orderTable;
    private OrderTable orderTableEmpty;

    @BeforeEach
    void setUp() {
        menuId1 = UUID.randomUUID();
        menuId2 = UUID.randomUUID();

        orderLineItems = new OrderLineItems(List.of(
                new OrderLineItem(menuId1, new Quantity(2), new Price(15_000)),
                new OrderLineItem(menuId2, new Quantity(1), new Price(20_000))
        ));
        orderTableRepository = new InMemoryOrderTableRepository();

        orderTableId = UUID.randomUUID();
        orderTable = new OrderTable(orderTableId, new DisplayedName("1번"), new NumberOfGuests(2), true);

        orderTableRepository.save(orderTable);

        orderTableEmptyId = UUID.randomUUID();
        orderTableEmpty = new OrderTable(orderTableEmptyId, new DisplayedName("1번"), new NumberOfGuests(0), false);

        orderTableRepository.save(orderTableEmpty);
    }

    @DisplayName("매장 주문 생성")
    @Test
    void createEatInOrder() {
        EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(new OrderTableEmptyTablePolicy(orderTableRepository));
        EatInOrder eatInOrder = eatInOrderFactory.createEatInOrder(orderTableId, orderLineItems);


        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTableId)
        );
    }

    @DisplayName("매장 주문 빈 테이블이 아니면 에러")
    @Test
    void eatInOrderEmptyOrderTable() {
        EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(new OrderTableEmptyTablePolicy(orderTableRepository));

        assertThatThrownBy(() -> eatInOrderFactory.createEatInOrder(orderTableEmptyId, orderLineItems)).isInstanceOf(IllegalStateException.class);
    }
}
