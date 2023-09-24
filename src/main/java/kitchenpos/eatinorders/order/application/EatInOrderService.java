package kitchenpos.eatinorders.order.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.order.application.dto.CreateOrderRequest;
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
    public EatInOrder create(final CreateOrderRequest request) {
        final EatInOrder eatInOrder = eatInOrderFactory.create(request.getOrderTableId(), request.getOrderLineItems());
        return orderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.serve();
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete(eatInOrderCompletePolicy);
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
