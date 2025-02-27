package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.OrderRepository;
import kitchenpos.eatinorder.application.port.out.OrderTableRepository;
import kitchenpos.eatinorder.application.service.model.CreateOrderTableRequest;
import kitchenpos.eatinorder.domain.model.OrderStatus;
import kitchenpos.eatinorder.domain.model.OrderTableEntity;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
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
    public OrderTable create(final CreateOrderTableRequest request) {
        OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), request.name(), profanities);
        OrderTableEntity orderTableEntity = orderTableRepository.save(OrderTableEntity.of(orderTable));
        return orderTableEntity.toDomain(profanities);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .orElseThrow(NoSuchElementException::new);
        orderTable.sit();
        OrderTableEntity orderTableEntity = orderTableRepository.save(OrderTableEntity.of(orderTable));
        return orderTableEntity.toDomain(profanities);
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTableEntity orderTableEntity = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTableEntity.getId(), EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        final OrderTable orderTable = orderTableEntity.toDomain(profanities);
        orderTable.clear();
        OrderTableEntity savedOrderTableEntity = orderTableRepository.save(OrderTableEntity.of(orderTable));
        return savedOrderTableEntity.toDomain(profanities);
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTableEntity request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        OrderTableEntity savedOrderTableEntity = orderTableRepository.save(OrderTableEntity.of(orderTable));
        return savedOrderTableEntity.toDomain(profanities);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll()
                .stream()
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderTable findById(UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .orElseThrow(NoSuchElementException::new);
    }
}
