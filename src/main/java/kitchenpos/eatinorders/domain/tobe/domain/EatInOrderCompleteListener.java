package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;

@DomainService
public class EatInOrderCompleteListener {
    private final EatInJpaOrderRepository orderRepository;
    private final ToBeOrderTableRepository orderTableRepository;

    public EatInOrderCompleteListener(EatInJpaOrderRepository orderRepository,
        ToBeOrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void clear(UUID orderMasterId) {
        EatInOrder eatInOrder = orderRepository.findByOrderMasterId(orderMasterId)
            .orElseThrow(() -> new IllegalArgumentException("매장 식사 주문 정보를 찾을 수 없습니다."));
        orderTableRepository.findById(eatInOrder.getOrderTableId())
            .ifPresent(ToBeOrderTable::initOrderTable);
    }
}
