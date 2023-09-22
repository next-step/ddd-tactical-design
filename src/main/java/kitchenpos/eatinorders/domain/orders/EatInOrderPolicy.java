package kitchenpos.eatinorders.domain.orders;

import kitchenpos.common.DomainService;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@DomainService
public class EatInOrderPolicy {

    private final OrderTableRepository orderTableRepository;

    public EatInOrderPolicy(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public void validOrderTableIdForOrder(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (orderTable.isClear()) {
            throw new IllegalStateException("비어있는 매장 테이블에는 주문을 할 수 없습니다. 테이블을 사용 상태로 변경 후 주문해주세요.");
        }
    }

    public void clearOrderTable(EatInOrder order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException("매장 테이블을 미사용 상태로 변경하는데 실패했습니다. 대상 매장 테이블이 존재하지 않습니다. orderTableId: " + order.getOrderTableId()));
        orderTable.clear();
    }
}
