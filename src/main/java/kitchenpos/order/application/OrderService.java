package kitchenpos.order.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.supports.strategy.OrderProcessStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderProcessStrategy orderProcessStrategy;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderProcessStrategy orderProcessStrategy) {
        this.orderRepository = orderRepository;
        this.orderProcessStrategy = orderProcessStrategy;
    }

    @Transactional
    public Order create(final Order request) {
        Order order = this.orderProcessStrategy.get(request).create(request);
        return orderRepository.save(order);
    }

    @Transactional
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(this.orderProcessStrategy.get(order).accept(order));
    }

    @Transactional
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(this.orderProcessStrategy.get(order).serve(order));
    }

    @Transactional
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(this.orderProcessStrategy.get(order).complete(order));
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(this.orderProcessStrategy.get(order).startDelivery(order));
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(this.orderProcessStrategy.get(order).completeDelivery(order));
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
