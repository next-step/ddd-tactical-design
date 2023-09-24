package kitchenpos.eatinorders.ordertable.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.eatinorders.ordertable.application.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ordertable.application.dto.CreateOrderTableRequest;
import kitchenpos.eatinorders.order.domain.EatInOrderRepository;
import kitchenpos.eatinorders.order.domain.OrderStatus;
import kitchenpos.eatinorders.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.domain.OrderTableRepository;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTable create(final CreateOrderTableRequest request) {
        return orderTableRepository.save(new OrderTable(UUID.randomUUID(), request.getName()));
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.use();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clean();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable findById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }
}
