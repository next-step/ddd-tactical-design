package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderPolicy;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.OrderTableClient;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItems;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuClient menuClient;
    private final OrderTableClient orderTableClient;
    private final EatInOrderPolicy eatInOrderPolicy;

    public EatInOrderService(
        final EatInOrderRepository orderRepository,
        final MenuClient menuClient,
        final OrderTableClient orderTableClient,
        final EatInOrderPolicy eatInOrderPolicy
    ) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
        this.orderTableClient = orderTableClient;
        this.eatInOrderPolicy = eatInOrderPolicy;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        OrderLineItems orderLineItems = OrderLineItems.of(request.getOrderLineItems(), menuClient);
        EatInOrder order = EatInOrder.create(orderLineItems, request.getOrderTableId(), orderTableClient);
        return orderRepository.save(order);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return order.accept();
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return order.serve();
    }


    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return order.complete(eatInOrderPolicy);
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }


    private EatInOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}
