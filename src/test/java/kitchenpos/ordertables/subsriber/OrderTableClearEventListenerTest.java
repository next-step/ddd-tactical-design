package kitchenpos.ordertables.subsriber;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.application.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.application.MenuLoader;
import kitchenpos.eatinorders.application.OrderTableStatusLoader;
import kitchenpos.eatinorders.application.service.DefaultMenuLoader;
import kitchenpos.eatinorders.application.service.DefaultOrderTableStatusLoader;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.publisher.OrderTableClearEvent;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.ordertables.application.EatInOrderStatusLoader;
import kitchenpos.ordertables.application.InMemoryOrderTableRepository;
import kitchenpos.ordertables.application.OrderTableService;
import kitchenpos.ordertables.application.service.DefaultEatInOrderStatusLoader;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static kitchenpos.eatinorders.fixture.EatInOrderFixture.order;
import static kitchenpos.ordertables.fixture.OrderTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@RecordApplicationEvents
@DisplayName("테이블 치우기 이벤트 발행")
class OrderTableClearEventListenerTest {

    @Autowired
    OrderTableRepository orderTableRepository;

    @Autowired
    EatInOrderRepository eatInOrderRepository;

    @Autowired
    EatInOrderService eatInOrderService;

    @Autowired
    OrderTableService orderTableService;

    @Autowired
    ApplicationEvents events;

    @DisplayName("주문이 완료되면, 주문 테이블 청소 이벤트가 발행된다.")
    @Test
    void completeEatInOrder() {
        assertThat(events.stream(OrderTableClearEvent.class)).hasSize(0);
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
        eatInOrderService.complete(expected.getId());
        assertThat(events.stream(OrderTableClearEvent.class)).hasSize(1);
    }

}
