package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.port.out.OrderRepository;
import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import kitchenpos.eatinorder.domain.model.Order;
import kitchenpos.eatinorder.domain.model.OrderTableEntity;
import kitchenpos.eatinorder.domain.model.OrderType;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
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

    public EatInOrderService(
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository,
            final MenuEatInOrderLineItemMapper menuEatInOrderLineItemMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuEatInOrderLineItemMapper = menuEatInOrderLineItemMapper;
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
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public Order serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.SERVED);
        return order;
    }


    @Transactional
    public Order complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        final EatInOrderStatus status = order.getStatus();

        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final OrderTableEntity orderTableEntity = orderTableRepository.findById(order.getOrderTableId()).orElseThrow();
            if (!orderRepository.existsByOrderTableAndStatusNot(orderTableEntity.getId(), EatInOrderStatus.COMPLETED)) {
                orderTableEntity.setNumberOfGuests(0);
                orderTableEntity.setOccupied(false);
            }
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
