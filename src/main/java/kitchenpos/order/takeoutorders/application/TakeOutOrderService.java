package kitchenpos.order.takeoutorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderAcceptService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCompleteService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCreateService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderServeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class TakeOutOrderService {

    private final OrderRepository orderRepository;
    private final TakeOutOrderCreateService takeOutOrderCreateService;
    private final TakeOutOrderAcceptService takeOutOrderAcceptService;
    private final TakeOutOrderServeService takeOutOrderServeService;
    private final TakeOutOrderCompleteService takeOutOrderCompleteService;

    public TakeOutOrderService(OrderRepository orderRepository, TakeOutOrderCreateService takeOutOrderCreateService, TakeOutOrderAcceptService takeOutOrderAcceptService, TakeOutOrderServeService takeOutOrderServeService, TakeOutOrderCompleteService takeOutOrderCompleteService) {
        this.orderRepository = orderRepository;
        this.takeOutOrderCreateService = takeOutOrderCreateService;
        this.takeOutOrderAcceptService = takeOutOrderAcceptService;
        this.takeOutOrderServeService = takeOutOrderServeService;
        this.takeOutOrderCompleteService = takeOutOrderCompleteService;
    }

    @Transactional(readOnly = false)
    public Order create(final Order request) {
        return this.takeOutOrderCreateService.create(request);
    }

    @Transactional(readOnly = false)
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.takeOutOrderAcceptService.accept(order);
    }

    @Transactional(readOnly = false)
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.takeOutOrderServeService.serve(order);
    }

    @Transactional(readOnly = false)
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.takeOutOrderCompleteService.complete(order);
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
