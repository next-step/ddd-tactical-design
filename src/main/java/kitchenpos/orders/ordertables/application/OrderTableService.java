package kitchenpos.orders.ordertables.application;

import kitchenpos.orders.ordertables.application.mapper.OrderTableMapper;
import kitchenpos.orders.ordertables.domain.NumberOfGuest;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableId;
import kitchenpos.orders.ordertables.domain.OrderTableRepository;
import kitchenpos.orders.ordertables.dto.OrderTableRequest;
import kitchenpos.orders.ordertables.dto.OrderTableResponse;
import kitchenpos.orders.ordertables.exception.OrderTableErrorCode;
import kitchenpos.orders.ordertables.exception.OrderTableException;
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
        return OrderTableMapper.toDto(orderTable);
    }

    @Transactional
    public OrderTableResponse sit(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);
        orderTable.sit();
        return OrderTableMapper.toDto(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final OrderTableId orderTableId) {
        final OrderTable orderTable = findById(orderTableId);

        if (eatInOrderStatusLoader.areAllOrdersComplete(orderTableId.getId())) {
            throw new OrderTableException(OrderTableErrorCode.NOT_COMPLETED_ORDER);
        }

        orderTable.clear();

        return OrderTableMapper.toDto(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final OrderTableId orderTableId, int numberOfGuest) {
        OrderTable orderTable = findById(orderTableId);
        orderTable.changeNumberOfGuest(new NumberOfGuest(numberOfGuest));
        return OrderTableMapper.toDto(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        List<OrderTable> orderTables = orderTableRepository.findAll();
        return OrderTableMapper.toDtos(orderTables);
    }

    private OrderTable findById(final OrderTableId orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }

}
