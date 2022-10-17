package kitchenpos.eatinorders.domain.tobe;

import java.util.NoSuchElementException;

public class JpaOrderTableCleanPolicy implements OrderTableCleanPolicy {
    private final OrderTableRepository orderTableRepository;

    public JpaOrderTableCleanPolicy(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void clear(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId.value())
            .orElseThrow(NoSuchElementException::new);

        orderTable.clear();
    }
}
