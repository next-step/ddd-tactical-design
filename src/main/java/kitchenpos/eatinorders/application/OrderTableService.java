package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.dto.OrderTableResponse;
import kitchenpos.eatinorders.todo.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderClient;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableName;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderClient orderClient;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderClient orderClient) {
        this.orderTableRepository = orderTableRepository;
        this.orderClient = orderClient;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableName request) {
        final OrderTable orderTable = OrderTable.from(request);
        return OrderTableResponse.from(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        orderTable.sit();
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        orderTable.clear(orderClient);
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final NumberOfGuests request) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        orderTable.changeNumberOfGuests(request);
        return OrderTableResponse.from(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll().stream()
                .map(OrderTableResponse::from)
                .toList();
    }

    private OrderTable getOrderTable(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }
}
