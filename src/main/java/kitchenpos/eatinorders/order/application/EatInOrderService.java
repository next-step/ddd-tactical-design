package kitchenpos.eatinorders.order.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.order.application.dto.CreateOrderRequest;
import kitchenpos.eatinorders.order.application.dto.OrderResponse;
import kitchenpos.eatinorders.order.domain.EatInOrder;
import kitchenpos.eatinorders.order.domain.EatInOrderCompletePolicy;
import kitchenpos.eatinorders.order.domain.EatInOrderFactory;
import kitchenpos.eatinorders.order.domain.EatInOrderRepository;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final EatInOrderFactory eatInOrderFactory;
    private final EatInOrderCompletePolicy eatInOrderCompletePolicy;

    public EatInOrderService(
            final EatInOrderRepository orderRepository,
            final EatInOrderFactory eatInOrderFactory,
            final EatInOrderCompletePolicy eatInOrderCompletePolicy) {
        this.orderRepository = orderRepository;
        this.eatInOrderFactory = eatInOrderFactory;
        this.eatInOrderCompletePolicy = eatInOrderCompletePolicy;
    }

    @Transactional
    public OrderResponse create(final CreateOrderRequest request) {
        final EatInOrder eatInOrder = eatInOrderFactory.create(request.getOrderTableId(), request.getOrderLineItems());
        orderRepository.save(eatInOrder);
        return new OrderResponse(eatInOrder);
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept();
        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.serve();
        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete(eatInOrderCompletePolicy);
        return new OrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
