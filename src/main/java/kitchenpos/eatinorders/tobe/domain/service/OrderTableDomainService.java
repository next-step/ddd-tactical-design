package kitchenpos.eatinorders.tobe.domain.service;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderTableDomainService {

    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableDomainService(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public void validateOrderTableClear(final UUID orderTableId) {
        if (eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
    }
}
