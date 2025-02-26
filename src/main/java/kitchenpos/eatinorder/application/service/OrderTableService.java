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
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        return orderTableRepository.save(OrderTableEntity.of(orderTable));
    }

    @Transactional
    public OrderTableEntity clear(final UUID orderTableId) {
        final OrderTableEntity orderTableEntity = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTableEntity, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        final OrderTable orderTable = orderTableEntity.toDomain(profanities);
        orderTable.clear();
        return orderTableRepository.save(OrderTableEntity.of(orderTable));
    }

    @Transactional
    public OrderTableEntity changeNumberOfGuests(final UUID orderTableId, final OrderTableEntity request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return orderTableRepository.save(OrderTableEntity.of(orderTable));
    }

    @Transactional(readOnly = true)
    public List<OrderTableEntity> findAll() {
        return orderTableRepository.findAll();
    }
}
