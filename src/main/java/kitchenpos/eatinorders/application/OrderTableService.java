package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.OrderTableCreateRequest;
import kitchenpos.eatinorders.ui.response.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(OrderTableRepository orderTableRepository, OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(OrderTableCreateRequest request) {
        OrderTable orderTable = new OrderTable(UUID.randomUUID(), request.getName());
        return OrderTableResponse.from(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        OrderTable orderTable = findOrderTableById(orderTableId);
        orderTable.setOccupied(true);
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.setNumberOfGuests(0);
        orderTable.setOccupied(false);
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        final int numberOfGuests = request.getNumberOfGuestsValue();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTable = findOrderTableById(orderTableId);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTable.setNumberOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable findOrderTableById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
