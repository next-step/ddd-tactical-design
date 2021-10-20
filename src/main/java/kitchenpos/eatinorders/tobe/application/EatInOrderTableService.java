package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.CreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class EatInOrderTableService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderTableService(final OrderRepository orderRepository, final OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable saveOrderTable(final OrderTable orderTable) {
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable clearTable(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);
        if (!existsByOrderTableAndStatusNot(orderTable)) {
            orderTable.clear();
        }
        return orderTable;
    }

    @Transactional
    public OrderTable clearTableWithException(final UUID orderTableId) {
        final OrderTable orderTable = findOrderTableById(orderTableId);
        if (existsByOrderTableAndStatusNot(orderTable)) {
            throw new IllegalStateException("완료되지 않은 주문이 남아있습니다.");
        }
        orderTable.clear();
        return orderTable;
    }

    @Transactional(readOnly = true)
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED);
    }

    @Transactional(readOnly = true)
    public OrderTable findOrderTableById(final UUID orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findOrderTableAll() {
        return orderTableRepository.findAll();
    }
}
