package kitchenpos.order.tobe.eatinorder.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.application.dto.ChangeNumberOfGuestRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.CreateOrderTableRequest;
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
    public OrderTable create(final CreateOrderTableRequest request) { // TODO(경록) : Response DTO 만들기
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) { // TODO(경록) : OrderTableName VO로 감싸보기!
            throw new IllegalArgumentException();
        }

        return orderTableRepository.save(OrderTable.empty(UUID.randomUUID(), name));
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) { // TODO(경록) : Response DTO 만들기
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeInUseTable();
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) { // TODO(경록) : Response DTO 만들기
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableIdAndStatusNot(orderTable.getId(), EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.clear();
        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final ChangeNumberOfGuestRequest request) { // TODO(경록) : Response DTO 만들기
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.changeNumberOfGuests(request.getNumberOfGuests()); // TODO(경록) : 도메인 객체에게 책임을 위임
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
