package kitchenpos.eatinorders.tobe.domain.order;

import java.util.NoSuchElementException;
import kitchenpos.common.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableValidator;

public class OrderTableManager {

    private final OrderTableRepository orderTableRepository;
    private final OrderTableValidator orderTableValidator;

    public OrderTableManager(
        final OrderTableRepository orderTableRepository,
        final OrderTableValidator orderTableValidator
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableValidator = orderTableValidator;
    }

    public void clear(final OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);

        try {
            orderTable.clear(orderTableValidator);
        } catch (IllegalStateException ignored) {
        }
    }
}
