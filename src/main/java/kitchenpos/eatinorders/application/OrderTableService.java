package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import kitchenpos.common.domain.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableRequest create(final OrderTableRequest request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTableRequest = new OrderTable();
        orderTableRequest.setId(UUID.randomUUID());
        orderTableRequest.setName(name);
        orderTableRequest.setNumberOfGuests(0);
        orderTableRequest.setOccupied(false);
        return orderTableRepository.save(orderTableRequest);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTableRequest.setOccupied(true);
        return orderTableRequest;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTableRequest, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTableRequest.setNumberOfGuests(0);
        orderTableRequest.setOccupied(false);
        return orderTableRequest;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTableRequest request) {
        final int numberOfGuests = request.getNumberOfGuests();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTableRequest = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (!orderTableRequest.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTableRequest.setNumberOfGuests(numberOfGuests);
        return orderTableRequest;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
