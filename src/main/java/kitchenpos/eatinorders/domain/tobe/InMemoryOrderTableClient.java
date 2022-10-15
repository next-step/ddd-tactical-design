package kitchenpos.eatinorders.domain.tobe;

import java.util.NoSuchElementException;

public class InMemoryOrderTableClient implements OrderTableClient {
    private final OrderTableRepository orderTableRepository;

    public InMemoryOrderTableClient(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void clear(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId.value())
            .orElseThrow(NoSuchElementException::new);

        orderTable.clear();
    }

    @Override
    public boolean isEmptyOrderTable(OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId.value())
            .orElseThrow(NoSuchElementException::new);

        return !orderTable.isOccupied();
    }
}
