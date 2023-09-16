package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;

@DomainService
public class EatInOrderRegisterListener {
    private final EatInOrderRepository orderRepository;
    private final ToBeOrderTableRepository orderTableRepository;

    public EatInOrderRegisterListener(EatInOrderRepository orderRepository,
        ToBeOrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void register(UUID orderMasterId, UUID orderTableId) {
        ToBeOrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NoSuchElementException("주문 테이블이 등록되어 있지 않습니다."));
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("빈 테이블에는 매장 주문을 등록할 수 없다.");
        }
        EatInOrder eatInOrder = new EatInOrder(orderMasterId, orderTableId);
        orderRepository.save((EatInOrder)eatInOrder);
    }
}
