package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableService(OrderTableRepository orderTableRepository, EatInOrderRepository eatInOrderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableRequest request) {
        request.validate();
        OrderTable orderTable = OrderTable.create(request.getName(), request.getNumberOfGuests());
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTable(orderTableId);
        orderTable.sit();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTable(orderTableId);
        if (eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTable.getId(), OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTableRequest request) {
        request.validate();
        final OrderTable orderTable = findOrderTable(orderTableId);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable findOrderTable(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
    }
}
