package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.port.out.OrderRepository;
import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import kitchenpos.eatinorder.domain.model.Order;
import kitchenpos.eatinorder.domain.model.OrderTableEntity;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper;
    private final OrderTableService orderTableService;

    public EatInOrderService(
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository,
            final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper, OrderTableService orderTableService
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuEatInOrderLineItemMapper = menuEatInOrderLineItemMapper;
        this.orderTableService = orderTableService;
    }

    @Transactional
    public Order create(final Order request) {
        final OrderTableEntity orderTable = orderTableRepository.findById(request.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        List<EatInOrderLineItem> eatInOrderLineItems = menuEatInOrderLineItemMapper.toEatInOrderLines(request.getOrderLineItems());
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), LocalDateTime.now(), eatInOrderLineItems, orderTable.getId());
        return orderRepository.save(Order.of(eatInOrder));
    }

    @Transactional
    public Order accept(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .map(Order::toDomain)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();
        return orderRepository.save(Order.of(eatInOrder));
    }

    @Transactional
    public Order serve(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .map(Order::toDomain)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();
        return orderRepository.save(Order.of(eatInOrder));
    }

    @Transactional
    public Order complete(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .map(Order::toDomain)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete();
        Order savedOrder = orderRepository.save(Order.of(eatInOrder));
        orderTableService.clear(eatInOrder.getOrderTableId());
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
