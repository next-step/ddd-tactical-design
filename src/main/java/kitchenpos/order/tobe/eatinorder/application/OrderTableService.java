package kitchenpos.order.tobe.eatinorder.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.application.dto.ChangeNumberOfGuestRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.CreateOrderTableRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.DetailOrderTableResponse;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public DetailOrderTableResponse create(final CreateOrderTableRequest request) {
        OrderTable orderTable = orderTableRepository.save(OrderTable.empty(UUID.randomUUID(), request.getName()));
        return DetailOrderTableResponse.of(orderTable);
    }

    @Transactional
    public DetailOrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeInUseTable();
        return DetailOrderTableResponse.of(orderTable);
    }

    @Transactional
    public DetailOrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableIdAndStatusNot(orderTable.getId(), EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return DetailOrderTableResponse.of(orderTable);
    }

    @Transactional
    public DetailOrderTableResponse changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestRequest request) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests());
        return DetailOrderTableResponse.of(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
