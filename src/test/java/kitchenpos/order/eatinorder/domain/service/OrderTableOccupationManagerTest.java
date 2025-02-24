package kitchenpos.order.eatinorder.domain.service;

import static kitchenpos.TestFixtureFactory.createMenu;
import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createOrderLineItem;
import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.model.ReleaseOrderTableEvent;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.infra.persistence.FakeEatInOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableOccupationManagerTest {

    @Test
    @DisplayName("매장 주문이 완료 되면 테이블의 점유 상태를 해지한다.")
    void release() {
        // given
        EatInOrderRepository eatInOrderRepository = new FakeEatInOrderRepository(new HashMap<>());
        OrderTableOccupationManager manager = new OrderTableOccupationManager(eatInOrderRepository);

        OrderTable orderTable = new OrderTable("1번 테이블", 3, true);
        EatInOrder eatInOrder = createEatInOrder(orderTable, EatInOrderFlow.COMPLETED);
        ReleaseOrderTableEvent event = new ReleaseOrderTableEvent(orderTable);

        eatInOrderRepository.save(eatInOrder);

        // when
        manager.release(event);

        // then
        assertThat(orderTable.isOccupied()).isEqualTo(false);
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(0);
    }

    @Test
    @DisplayName("매장 주문이 완료 되지 않은 경우, 테이블 점유 상태를 해지하려 하면 예외가 발생한다.")
    void release_exception() {
        // given
        EatInOrderRepository eatInOrderRepository = new FakeEatInOrderRepository(new HashMap<>());
        OrderTableOccupationManager manager = new OrderTableOccupationManager(eatInOrderRepository);

        OrderTable orderTable = new OrderTable("1번 테이블", 3, true);
        EatInOrder eatInOrder = createEatInOrder(orderTable, EatInOrderFlow.SERVED);
        ReleaseOrderTableEvent event = new ReleaseOrderTableEvent(orderTable);

        eatInOrderRepository.save(eatInOrder);

        // when // then
        assertThatThrownBy(() -> manager.release(event))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문이 완료되지 않은 매장 테이블은 정리할 수 없습니다.");
    }

    private EatInOrder createEatInOrder(OrderTable orderTable, EatInOrderFlow eatInOrderFlow) {
        List<OrderLineItem> orderLineItems = List.of(createOrderLineItem(createMenu(createMenuGroup(), createProduct(
                BigDecimal.TWO), 3)));
        EatInOrder eatInOrder = new EatInOrder(LocalDateTime.now(),
                orderLineItems,
                eatInOrderFlow);
        eatInOrder.occupyOrderTable(orderTable);
        return eatInOrder;
    }
}
