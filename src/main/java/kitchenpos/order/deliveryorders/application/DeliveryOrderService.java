package kitchenpos.order.deliveryorders.application;

import kitchenpos.order.deliveryorders.domain.*;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeliveryOrderService {

    private final OrderRepository orderRepository;
    private final DeliveryOrderCreateService deliveryOrderCreateService;
    private final DeliveryOrderAcceptService deliveryOrderAcceptService;
    private final DeliveryOrderServeService deliveryOrderServeService;
    private final DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService;
    private final DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService;
    private final DeliveryOrderCompleteService deliveryOrderCompleteService;

    public DeliveryOrderService(OrderRepository orderRepository, DeliveryOrderCreateService deliveryOrderCreateService, DeliveryOrderAcceptService deliveryOrderAcceptService, DeliveryOrderServeService deliveryOrderServeService, DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService, DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService, DeliveryOrderCompleteService deliveryOrderCompleteService) {
        this.orderRepository = orderRepository;
        this.deliveryOrderCreateService = deliveryOrderCreateService;
        this.deliveryOrderAcceptService = deliveryOrderAcceptService;
        this.deliveryOrderServeService = deliveryOrderServeService;
        this.deliveryOrderStartDeliveryService = deliveryOrderStartDeliveryService;
        this.deliveryOrderCompleteDeliveryService = deliveryOrderCompleteDeliveryService;
        this.deliveryOrderCompleteService = deliveryOrderCompleteService;
    }

    @Transactional(readOnly = false)
    public Order create(final Order request) {
        return this.deliveryOrderCreateService.create(request);
    }

    @Transactional(readOnly = false)
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.deliveryOrderAcceptService.accept(order);
    }

    @Transactional(readOnly = false)
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.deliveryOrderServeService.serve(order);
    }

    @Transactional(readOnly = false)
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.deliveryOrderCompleteService.complete(order);
    }

    @Transactional(readOnly = false)
    public Order startDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.deliveryOrderStartDeliveryService.startDelivery(order);
    }

    @Transactional(readOnly = false)
    public Order completeDelivery(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.deliveryOrderCompleteDeliveryService.completeDelivery(order);
    }

    @Transactional(readOnly = true)
    public Order getOrder(final UUID orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
