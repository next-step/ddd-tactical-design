package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.port.out.OrderRepository;
import kitchenpos.eatinorder.adapter.out.persistance.entity.Order;
import kitchenpos.eatinorder.application.service.model.CreateEatInOrderRequest;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.eatinorder.domain.model.todo.OrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper;
    private final OrderTableService orderTableService;

    public EatInOrderService(
            final OrderRepository orderRepository,
            final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper,
            final OrderTableService orderTableService
    ) {
        this.orderRepository = orderRepository;
        this.menuEatInOrderLineItemMapper = menuEatInOrderLineItemMapper;
        this.orderTableService = orderTableService;
    }

    @Transactional
    public EatInOrder create(final CreateEatInOrderRequest request) {
        OrderTable orderTable = orderTableService.findById(request.orderTableId());
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        List<EatInOrderLineItem> eatInOrderLineItems = menuEatInOrderLineItemMapper.toEatInOrderLines(request.orderLineItems());
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), LocalDateTime.now(), eatInOrderLineItems, orderTable.getId());
        Order save = orderRepository.save(Order.of(eatInOrder));
        return save.toDomain();
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .map(Order::toDomain)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();
        Order save = orderRepository.save(Order.of(eatInOrder));
        return save.toDomain();
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
