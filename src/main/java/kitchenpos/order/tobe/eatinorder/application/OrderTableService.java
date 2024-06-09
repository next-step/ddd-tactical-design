package kitchenpos.order.tobe.eatinorder.application;


import kitchenpos.order.tobe.eatinorder.application.dto.request.OrderTableChangeNumberRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.request.OrderTableCreateRequest;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.OrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("newOrderTableService")
public class OrderTableService {
    private final EatInOrderRepository eatInOrderRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderTableService(EatInOrderRepository eatInOrderRepository, OrderTableRepository orderTableRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableCreateRequest request) {
        final OrderTable orderTable = new OrderTable(UUID.randomUUID(), request.name());

        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);

        orderTable.sit();

        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);

        if (eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }

        orderTable.clear();

        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTableChangeNumberRequest request) {
        final int numberOfGuests = request.numberOfGuest();
        final OrderTable orderTable = findOrderTableById(orderTableId);

        orderTable.changeNumberOfGuests(numberOfGuests);

        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }

    private OrderTable findOrderTableById(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException("Order table not found"));
    }
}
