package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderCompleteEvent;
import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import kitchenpos.eatinorders.domain.OrderEventService;

import java.util.NoSuchElementException;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NOT_FOUND_ORDER_TABLE;

public class MockOrderEventService implements OrderEventService {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public MockOrderEventService(EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Override
    public void notifyOrderComplete(OrderCompleteEvent event) {
        EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(event.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
        eatInOrderTable.clear();
    }
}
