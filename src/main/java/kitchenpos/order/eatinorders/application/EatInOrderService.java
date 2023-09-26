package kitchenpos.order.eatinorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.eatinorders.domain.EatInOrderAcceptService;
import kitchenpos.order.eatinorders.domain.EatInOrderCompleteService;
import kitchenpos.order.eatinorders.domain.EatInOrderCreateService;
import kitchenpos.order.eatinorders.domain.EatInOrderServeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class EatInOrderService {

    private final OrderRepository orderRepository;
    private final EatInOrderCreateService eatInOrderCreateService;
    private final EatInOrderAcceptService eatInOrderAcceptService;
    private final EatInOrderServeService eatInOrderServeService;
    private final EatInOrderCompleteService eatInOrderCompleteService;

    public EatInOrderService(OrderRepository orderRepository, EatInOrderCreateService eatInOrderCreateService, EatInOrderAcceptService eatInOrderAcceptService, EatInOrderServeService eatInOrderServeService, EatInOrderCompleteService eatInOrderCompleteService) {
        this.orderRepository = orderRepository;
        this.eatInOrderCreateService = eatInOrderCreateService;
        this.eatInOrderAcceptService = eatInOrderAcceptService;
        this.eatInOrderServeService = eatInOrderServeService;
        this.eatInOrderCompleteService = eatInOrderCompleteService;
    }

    @Transactional(readOnly = false)
    public Order create(Order request) {
        return this.eatInOrderCreateService.create(request);
    }

    @Transactional(readOnly = false)
    public Order accept(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.eatInOrderAcceptService.accept(order);
    }

    @Transactional(readOnly = false)
    public Order serve(final UUID orderId) {
        Order order = getOrder(orderId);
        return this.eatInOrderServeService.serve(order);
    }

    @Transactional(readOnly = false)
    public Order complete(final UUID orderId) {
        Order order = getOrder(orderId);
        return eatInOrderCompleteService.complete(order);
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
