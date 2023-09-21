package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;

@DomainService
public class EatInOrderCompleteListener {
    private final ToBeOrderTableRepository orderTableRepository;

    public EatInOrderCompleteListener(ToBeOrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void clear(UUID orderTableId) {
        orderTableRepository.findById(orderTableId)
            .ifPresent(ToBeOrderTable::initOrderTable);
    }
}
