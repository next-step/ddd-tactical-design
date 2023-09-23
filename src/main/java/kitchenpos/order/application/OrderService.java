package kitchenpos.order.application;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.eatinorders.domain.OrderTableClearService;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KitchenridersClient kitchenridersClient;
    private final OrderTableClearService orderTableClearService;
    private final OrderCreateFactory orderCreateFactory;

    public OrderService(
            final OrderRepository orderRepository,
            final KitchenridersClient kitchenridersClient,
            final OrderTableClearService orderTableClearService,
            final OrderCreateFactory orderCreateFactory) {
        this.orderRepository = orderRepository;
        this.kitchenridersClient = kitchenridersClient;
        this.orderTableClearService = orderTableClearService;
        this.orderCreateFactory = orderCreateFactory;
    }

    @Transactional
    public Order create(final Order request) {
        Order order = orderCreateFactory.createOrder(request);
        return orderRepository.save(order);
    }

    @Transactional
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.accept(kitchenridersClient));
    }

    @Transactional
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.serve());
    }

    @Transactional
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.complete(orderTableClearService));
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderRepository.save(order.startDelivery());
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderRepository.save(order.completeDelivery());
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
