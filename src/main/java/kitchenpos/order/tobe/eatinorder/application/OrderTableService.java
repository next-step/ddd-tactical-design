package kitchenpos.order.tobe.eatinorder.application;


import kitchenpos.order.tobe.eatinorder.application.dto.request.OrderTableChangeNumberRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.request.OrderTableCreateRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.response.OrderTableResponse;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;
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
    public OrderTableResponse create(final OrderTableCreateRequest request) {
        final OrderTable orderTable = new OrderTable(UUID.randomUUID(), request.name());

        return toOrderTableResponse(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);

        orderTable.sit();

        return toOrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);

        if (eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException("주문이 완료되지 않은 테이블은 초기화할 수 없습니다.");
        }

        orderTable.clear();

        return toOrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableChangeNumberRequest request) {
        final int numberOfGuests = request.numberOfGuest();
        final OrderTable orderTable = findOrderTableById(orderTableId);

        orderTable.changeNumberOfGuests(numberOfGuests);

        return toOrderTableResponse(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll().stream()
                .map(this::toOrderTableResponse)
                .toList();
    }

    private OrderTable findOrderTableById(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException("주문 테이블을 찾을 수 없습니다."));
    }

    private OrderTableResponse toOrderTableResponse(OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getId(),
                orderTable.getName(),
                orderTable.getNumberOfGuests(),
                orderTable.isOccupied()
        );
    }
}
