package kitchenpos.order.common.application;

import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.DELIVERED;
import static kitchenpos.order.eatinorder.domain.EatInOrderStatus.SERVED;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.common.infra.InMemoryOrderRepository;
import kitchenpos.order.eatinorder.domain.OrderTable;
import kitchenpos.order.eatinorder.domain.OrderTableRepository;
import kitchenpos.order.eatinorder.infra.InMemoryOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderService = new OrderService(orderRepository);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        orderRepository.save(order(SERVED, orderTable));
        orderRepository.save(order(DELIVERED, "서울시 송파구 위례성대로 2"));
        final List<Order> actual = orderService.findAll();
        assertThat(actual).hasSize(2);
    }
}
