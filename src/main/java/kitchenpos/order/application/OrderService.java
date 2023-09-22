package kitchenpos.order.application;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.eatinorders.domain.OrderTableClearService;
import kitchenpos.order.eatinorders.domain.OrderTableRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final KitchenridersClient kitchenridersClient;
    private final OrderLineItemsService orderLineItemsService;
    private final OrderTableRepository orderTableRepository;
    private final OrderTableClearService orderTableClearService;

    public OrderService(
            final OrderRepository orderRepository,
            final ApplicationEventPublisher publisher,
            final KitchenridersClient kitchenridersClient,
            final OrderLineItemsService orderLineItemsService,
            final OrderTableRepository orderTableRepository,
            final OrderTableClearService orderTableClearService) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
        this.kitchenridersClient = kitchenridersClient;
        this.orderLineItemsService = orderLineItemsService;
        this.orderTableRepository = orderTableRepository;
        this.orderTableClearService = orderTableClearService;
    }

    @Transactional
    public Order create(final Order request) {
        return orderRepository.save(request.create(orderLineItemsService, orderTableRepository));
    }

    @Transactional
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.accept(publisher, kitchenridersClient));
    }

    @Transactional
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.serve(publisher));
    }

    @Transactional
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return orderRepository.save(order.complete(publisher, orderTableClearService));
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderRepository.save(order.startDelivery(publisher));
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.orderRepository.save(order.completeDelivery(publisher));
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
