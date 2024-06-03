package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.orders.OrderTableClient;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.support.domain.MenuClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuClient menuClient;
    private final OrderTableClient orderTableClient;

    public EatInOrderService(
        final EatInOrderRepository orderRepository,
        final MenuClient menuClient,
        final OrderTableClient orderTableClient
    ) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
        this.orderTableClient = orderTableClient;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        EatInOrder order = EatInOrder.create(request, menuClient, orderTableClient);
        return orderRepository.save(order);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.SERVED);
        return order;
    }


    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final EatInOrderStatus status = order.getStatus();
            if (status != EatInOrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        order.setStatus(EatInOrderStatus.COMPLETED);
        final OrderTable orderTable = orderTableClient.getOrderTable(order.getOrderTableId());
        if (!orderRepository.existsByOrderTableIdAndStatusNot(order.getOrderTableId(), EatInOrderStatus.COMPLETED)) {
            orderTable.clear();
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
