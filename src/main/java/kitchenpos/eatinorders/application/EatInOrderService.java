package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderPolicy;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.OrderTableClient;
import kitchenpos.support.application.OrderLineItemsFactory;
import kitchenpos.support.domain.MenuClient;
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
    public EatInOrderResponse create(final EatInOrderCreateRequest request) {
        EatInOrder order = EatInOrder.create(
                new OrderLineItemsFactory(menuClient).create(request.orderLineItems()),
                request.orderTableId(), orderTableClient);
        return EatInOrderResponse.from(orderRepository.save(order));
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return EatInOrderResponse.from(order.accept());
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return EatInOrderResponse.from(order.serve());
    }

    @Transactional
    public EatInOrderResponse complete(final UUID orderId) {
        final EatInOrder order = getOrder(orderId);
        return EatInOrderResponse.from(order.complete(eatInOrderPolicy));
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(EatInOrderResponse::from)
                .toList();
    }

    private EatInOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}
