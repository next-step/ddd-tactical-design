package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.application.dto.OrderTableResponse;
import kitchenpos.eatinorders.domain.order.OrderRepository;
import kitchenpos.eatinorders.domain.order.OrderStatus;
import kitchenpos.eatinorders.domain.ordertable.GuestNumber;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRequest request) {
        final OrderTable orderTable = new OrderTable(Name.of(request.getName()));
        return new OrderTableResponse(orderTableRepository.save(orderTable));
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        orderTable.occupy();
        return new OrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse clear(final UUID orderTableId) {
        final OrderTable orderTable = getOrderTable(orderTableId);
        validateCompletedOrder(orderTable);
        orderTable.clear();
        return new OrderTableResponse(orderTable);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final OrderTableRequest request) {
        final GuestNumber numberOfGuests = GuestNumber.of(request.getNumberOfGuests());
        final OrderTable orderTable = getOrderTable(orderTableId);

        validateEmptyTable(orderTable);
        orderTable.changeNumberOfGuests(numberOfGuests);
        return new OrderTableResponse(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> findAll() {
        return orderTableRepository.findAll().stream()
                .map(OrderTableResponse::new)
                .collect(Collectors.toList());
    }

    private void validateCompletedOrder(final OrderTable orderTable) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException("완료되지 않은 주문이 있는 테이블입니다.");
        }
    }

    private OrderTable getOrderTable(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException("입력한 주문테이블이 존재하지 않습니다."));
    }

    private static void validateEmptyTable(final OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("주문 테이블이 비어 있습니다.");
        }
    }
}
