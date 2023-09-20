package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final EatInOrderCreateService eatInOrderCreateService;
    private final EatInOrderAcceptService eatInOrderAcceptService;
    private final EatInOrderServeService eatInOrderServeService;
    private final EatInOrderCompleteService eatInOrderCompleteService;
    public EatInOrderService(
            final OrderRepository orderRepository,
            final EatInOrderCreateService eatInOrderCreateService,
            final EatInOrderAcceptService eatInOrderAcceptService,
            final EatInOrderServeService eatInOrderServeService,
            final EatInOrderCompleteService eatInOrderCompleteService) {
        this.orderRepository = orderRepository;
        this.eatInOrderCreateService = eatInOrderCreateService;
        this.eatInOrderAcceptService = eatInOrderAcceptService;
        this.eatInOrderServeService = eatInOrderServeService;
        this.eatInOrderCompleteService = eatInOrderCompleteService;
    }

    @Transactional
    public Order create(final Order request) {
        return eatInOrderCreateService.create(request);
    }

    @Transactional
    public Order accept(final UUID orderId) {
        return this.eatInOrderAcceptService.accept(getOrder(orderId));
    }

    @Transactional
    public Order serve(final UUID orderId) {
       return this.eatInOrderServeService.serve(getOrder(orderId));
    }

    @Transactional
    public Order complete(final UUID orderId) {
        return this.eatInOrderCompleteService.complete(getOrder(orderId));
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (order.getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.DELIVERING);
        return order;
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.DELIVERED);
        return order;
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
