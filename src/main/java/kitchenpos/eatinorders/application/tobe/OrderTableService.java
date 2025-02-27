package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.application.tobe.exception.InvalidOrderTableStateException;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.ui.dto.OrderTableChangeNumberOfGuestsResponse;
import kitchenpos.eatinorders.ui.dto.OrderTableCreateResponse;
import kitchenpos.eatinorders.ui.dto.OrderTableSitResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableService(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTableCreateResponse create(final String tableName) {
        OrderTable orderTable = orderTableRepository.save(OrderTable.create(tableName));
        return OrderTableCreateResponse.from(orderTable);
    }

    @Transactional
    public OrderTableSitResponse sit(final OrderTableId id) {
        final OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        orderTable.sit();
        return OrderTableSitResponse.from(orderTable);
    }

    //TODO orderRepository 개발 후 진행
    /*@Transactional
    public OrderTable clear(final OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        orderTable.setNumberOfGuests(0);
        orderTable.setOccupied(false);
        return orderTable;
    }*/

    @Transactional
    public OrderTableChangeNumberOfGuestsResponse changeNumberOfGuests(final OrderTableId id, final int numberOfGuests) {
        final OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (orderTable.isNotOccupied()) {
            throw new InvalidOrderTableStateException("빈 주문 테이블은 손님의 수를 변경할 수 없습니다");
        }
        orderTable.changeNumberOfGuests(numberOfGuests);
        return OrderTableChangeNumberOfGuestsResponse.from(orderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
