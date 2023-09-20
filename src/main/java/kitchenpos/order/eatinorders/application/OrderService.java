package kitchenpos.order.eatinorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.strategy.OrderStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStrategy orderStrategy;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderStrategy orderStrategy) {
        this.orderRepository = orderRepository;
        this.orderStrategy = orderStrategy;
    }

    @Transactional
    public Order create(final Order request) {
        return this.orderStrategy.get(request.getType()).create(request);
    }

    @Transactional
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderStrategy.get(order.getType()).accept(order);
    }

    @Transactional
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderStrategy.get(order.getType()).serve(order);
    }

    @Transactional
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderStrategy.get(order.getType()).complete(order);
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderStrategy.get(order.getType()).startDelivery(order);
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderStrategy.get(order.getType()).completeDelivery(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrder(final UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

}
