package kitchenpos.eatinorders.application.orderTables;

import kitchenpos.eatinorders.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.domain.orders.OrderStatus;
import kitchenpos.eatinorders.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableName;
import kitchenpos.eatinorders.domain.ordertables.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableName name) {
        final OrderTable orderTable = OrderTable.createNew(name);
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final NumberOfGuests numberOfGuests) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
