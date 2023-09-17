package kitchenpos.takeoutorders.domain;

import java.util.UUID;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kitchenpos.common.DomainService;

@DomainService
public class TakeOutOrderRegisterListener {
    private final TakeOutOrderRepository orderRepository;

    public TakeOutOrderRegisterListener(TakeOutOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void register(UUID orderMasterId) {
        orderRepository.save(new TakeOutOrder(orderMasterId));
    }
}
