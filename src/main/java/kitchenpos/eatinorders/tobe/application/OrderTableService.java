package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.request.OrderTableChangeRequest;
import kitchenpos.eatinorders.tobe.application.dto.request.OrderTableCreateRequest;
import kitchenpos.eatinorders.tobe.application.dto.response.OrderTableResponse;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableCreateRequest request) {
        final OrderTable orderTable = new OrderTable(request.getName(), 0, false);
        return OrderTableResponse.of(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.occupied();
        return OrderTableResponse.of(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return OrderTableResponse.of(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableChangeRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return OrderTableResponse.of(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll().stream()
            .map(OrderTableResponse::of)
            .collect(Collectors.toList());
    }
}