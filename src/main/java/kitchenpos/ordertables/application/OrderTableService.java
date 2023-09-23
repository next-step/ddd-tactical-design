package kitchenpos.ordertables.application;

import kitchenpos.ordertables.domain.NumberOfGuest;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;
import kitchenpos.ordertables.dto.OrderTableRequest;
import kitchenpos.ordertables.dto.OrderTableResponse;
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
    public OrderTableResponse create(final OrderTableRequest request) {
        OrderTable orderTable = orderTableRepository.save(request.toEntity());
        return OrderTableResponse.fromEntity(orderTable);
    }

    @Transactional
    public OrderTableResponse sit(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.sit();
        return OrderTableResponse.fromEntity(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);

        if (eatInOrderStatusLoader.areAllOrdersComplete(orderTableId.getId())) {
            throw new OrderTableException(OrderTableErrorCode.NOT_COMPLETED_ORDER);
        }

        orderTable.clear();

        return OrderTableResponse.fromEntity(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final OrderTableId orderTableId, int numberOfGuest) {
        OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuest(new NumberOfGuest(numberOfGuest));
        return OrderTableResponse.fromEntity(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        List<OrderTable> orderTables = orderTableRepository.findAll();
        return OrderTableResponse.fromEntities(orderTables);
    }

    private OrderTable findById(final OrderTableId orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }


}
