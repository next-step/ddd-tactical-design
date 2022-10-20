package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.annotation.DomainService;

import java.util.UUID;

@DomainService
public class OrderTableCleanUp {

    private final CleanUpPolicy cleanUpPolicy;
    private final OrderTableRepository orderTableRepository;

    public OrderTableCleanUp(final CleanUpPolicy cleanUpPolicy, final OrderTableRepository orderTableRepository) {
        this.cleanUpPolicy = cleanUpPolicy;
        this.orderTableRepository = orderTableRepository;
    }

    public void clear(final UUID orderTableId) {
        if (!cleanUpPolicy.isCleanUpCondition(orderTableId)) {
            throw new IllegalArgumentException("정리할 수 있는 조건이 아닙니다.");
        }
        final OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
        orderTable.clear();
    }
}
