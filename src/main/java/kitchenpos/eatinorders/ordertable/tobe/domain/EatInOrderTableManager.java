package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.eatinorders.order.tobe.domain.OrderTableManager;

import java.util.UUID;

@DomainService
public class EatInOrderTableManager implements OrderTableManager {

    private final OrderTableRepository orderTableRepository;

    public EatInOrderTableManager(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public boolean isEmptyTable(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
        return orderTable.isEmptyTable();
    }
}
