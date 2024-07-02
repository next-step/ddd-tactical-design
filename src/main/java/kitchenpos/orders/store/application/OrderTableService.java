package kitchenpos.orders.store.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.NumberOfGuests;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.OrderTableName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final StoreOrderRepository storeOrderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository,
            final StoreOrderRepository storeOrderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.storeOrderRepository = storeOrderRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableName request) {
        return orderTableRepository.save(new OrderTable(request));
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (storeOrderRepository.existsByOrderTableAndStatusNot(orderTable,
                OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final NumberOfGuests request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(request);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
