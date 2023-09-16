package kitchenpos.ordertables.application;

import kitchenpos.ordertables.domain.NumberOfGuest;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;
import kitchenpos.ordertables.dto.OrderTableRequest;
import kitchenpos.ordertables.exception.OrderTableErrorCode;
import kitchenpos.ordertables.exception.OrderTableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderStatusLoader eatInOrderStatusLoader;

    public OrderTableService(OrderTableRepository orderTableRepository, EatInOrderStatusLoader eatInOrderStatusLoader) {
        this.orderTableRepository = orderTableRepository;
        this.eatInOrderStatusLoader = eatInOrderStatusLoader;
    }

    @Transactional
    public OrderTable create(final OrderTableRequest request) {
        return orderTableRepository.save(request.toEntity());
    }

    @Transactional
    public OrderTable sit(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.sit();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);

        if (eatInOrderStatusLoader.areAllOrdersComplete(orderTableId.getId())) {
            throw new OrderTableException(OrderTableErrorCode.NOT_COMPLETED_ORDER);
        }

        orderTable.clear();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final OrderTableId orderTableId, int numberOfGuest) {
        OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuest(new NumberOfGuest(numberOfGuest));
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderTable findById(final OrderTableId orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }


}
