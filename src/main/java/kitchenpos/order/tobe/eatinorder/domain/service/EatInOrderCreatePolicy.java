package kitchenpos.order.tobe.eatinorder.domain.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderCreatePolicy {

    private final OrderTableRepository orderTableRepository;

    public EatInOrderCreatePolicy(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public void validOrderTableId(UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(NoSuchElementException::new);
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("빈 테이블은 주문을 할 수 없습니다.");
        }
    }
}
