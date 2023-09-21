package kitchenpos.eatinorders.infrastructure;

import kitchenpos.eatinorders.domain.orders.OrderTableClient;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class OrderTableClientImpl implements OrderTableClient {

    private final OrderTableRepository orderTableRepository;

    public OrderTableClientImpl(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void validOrderTableIdForOrder(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void clearOrderTable(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException("매장 테이블을 미사용 상태로 변경하는데 실패했습니다. 대상 매장 테이블이 존재하지 않습니다. orderTableId: " + orderTableId));
        orderTable.clear();
    }
}
