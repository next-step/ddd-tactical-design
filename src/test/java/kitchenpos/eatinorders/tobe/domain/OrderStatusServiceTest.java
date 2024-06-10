package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.application.InMemoryEatInOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusServiceTest {

    private EatInOrderRepository eatInOrderRepository;
    private OrderStatusService orderStatusService;

    private OrderLineItems orderLineItems;

    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        this.eatInOrderRepository = new InMemoryEatInOrderRepository();
        this.orderStatusService = new OrderStatusService(eatInOrderRepository);

        this.orderTable = new OrderTable("1번");
        OrderLineItem orderLineitem = new OrderLineItem(UUID.randomUUID(), 1, 10_000);
        this.orderLineItems = new OrderLineItems(List.of(orderLineitem));
    }

    @Test
    @DisplayName("테이블의 주문이 완료 상태이다.")
    void isCompletedInOrderTable() {
        //given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());
        eatInOrderRepository.save(eatInOrder);
        eatInOrder.accepted();
        eatInOrder.served();
        eatInOrder.complete();

        //when
        boolean isCompleted = orderStatusService.isCompletedInOrderTable(orderTable.getId());

        //then
        assertThat(isCompleted).isTrue();

    }

    @Test
    @DisplayName("테이블의 주문이 완료상태가 아니다.")
    void isNotCompletedInOrderTable() {
        //given
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTable.getId());
        eatInOrderRepository.save(eatInOrder);
        eatInOrder.accepted();
        eatInOrder.served();

        //when
        boolean isCompleted = orderStatusService.isCompletedInOrderTable(orderTable.getId());

        //then
        assertThat(isCompleted).isFalse();

    }
}
