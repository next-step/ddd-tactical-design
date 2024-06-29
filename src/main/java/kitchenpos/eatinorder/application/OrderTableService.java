package kitchenpos.eatinorder.application;

import kitchenpos.eatinorder.tobe.domain.order.ClearedTable;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableName;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;
    private final ClearedTable clearedTable;

    public OrderTableService(final OrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository, ClearedTable clearedTable) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
        this.clearedTable = clearedTable;
    }

    @Transactional
    public OrderTable create(final OrderTable request) {
        final String name = request.getName();
        final var orderTable = OrderTable.of(OrderTableName.of(name));
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        orderTable.sitted();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final var orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        orderTable.cleared(clearedTable);

        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();

        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        orderTable.changeNumOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
