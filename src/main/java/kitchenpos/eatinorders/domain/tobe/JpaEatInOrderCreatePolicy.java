package kitchenpos.eatinorders.domain.tobe;

import java.util.NoSuchElementException;

public class JpaEatInOrderCreatePolicy implements EatInOrderCreatePolicy {

    private final OrderTableRepository orderTableRepository;

    public JpaEatInOrderCreatePolicy(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public boolean isOccupiedOrderTable(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId.value())
            .orElseThrow(NoSuchElementException::new);

        return orderTable.isOccupied();
    }
}
