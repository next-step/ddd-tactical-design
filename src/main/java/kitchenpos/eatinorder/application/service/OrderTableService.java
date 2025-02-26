package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.OrderRepository;
import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import kitchenpos.eatinorder.domain.model.OrderStatus;
import kitchenpos.eatinorder.domain.model.OrderTableEntity;
import kitchenpos.eatinorder.domain.model.todo.OrderTable;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;
    private final Profanities profanities;

    public OrderTableService(
            final OrderTableRepository orderTableRepository,
            final OrderRepository orderRepository,
            final Profanities profanities
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
        this.profanities = profanities;
    }

    @Transactional
    public OrderTableEntity create(final OrderTableEntity request) {
        OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), request.getName(), profanities);
        return orderTableRepository.save(OrderTableEntity.of(orderTable));
    }

    @Transactional
    public OrderTableEntity sit(final UUID orderTableId) {
        final OrderTableEntity orderTableEntity = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTableEntity.setOccupied(true);
        return orderTableEntity;
    }

    @Transactional
    public OrderTableEntity clear(final UUID orderTableId) {
        final OrderTableEntity orderTableEntity = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTableEntity, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTableEntity.setNumberOfGuests(0);
        orderTableEntity.setOccupied(false);
        return orderTableEntity;
    }

    @Transactional
    public OrderTableEntity changeNumberOfGuests(final UUID orderTableId, final OrderTableEntity request) {
        final int numberOfGuests = request.getNumberOfGuests();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final OrderTableEntity orderTableEntity = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (!orderTableEntity.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTableEntity.setNumberOfGuests(numberOfGuests);
        return orderTableEntity;
    }

    @Transactional(readOnly = true)
    public List<OrderTableEntity> findAll() {
        return orderTableRepository.findAll();
    }
}
