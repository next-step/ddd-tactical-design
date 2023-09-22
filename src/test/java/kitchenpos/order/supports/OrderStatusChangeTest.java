package kitchenpos.order.supports;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.eatinorders.domain.EatInOrderServeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static kitchenpos.Fixtures.order;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RecordApplicationEvents
@SpringBootTest
class OrderStatusChangeTest {
    @Autowired
    private EatInOrderServeService eatInOrderServeService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ApplicationEvents applicationEvents;

    @Test
    void serve_test() {
        final UUID orderId = orderRepository.save(order(OrderStatus.ACCEPTED)).getId();
        this.eatInOrderServeService.serve(orderRepository.findById(orderId).orElseThrow());
        final OrderStatusChange actual = applicationEvents.stream(OrderStatusChange.class).findAny().orElseThrow();
        assertThat(actual).isNotNull();
    }
}