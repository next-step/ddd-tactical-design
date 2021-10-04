package kitchenpos.eatinorders.tobe.ordertable.application;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.CreateRequest;
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
    public OrderTable create(final CreateRequest request) {
        final OrderTable orderTable = request.toEntity();
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.sit();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
//        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
//            throw new IllegalStateException();
//        }
        orderTable.clear();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestsRequest request) {
        final int numberOfGuests = request.getNumberOfGuests();
        final OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public OrderTable findById(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
