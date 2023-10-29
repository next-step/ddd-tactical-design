package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableChecker;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_FOUND_ORDER_TABLE;
import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_OCCUPIED_ORDER_TABLE;

public class MockEatInOrderTableChecker implements EatInOrderTableChecker {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public MockEatInOrderTableChecker(EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Override
    public UUID validate(UUID orderTableId) {
        EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
        if (eatInOrderTable.isEmpty()) {
            throw new IllegalStateException(NOT_OCCUPIED_ORDER_TABLE);
        }
        return orderTableId;
    }
}
