package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableChecker;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_FOUND_ORDER_TABLE;
import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_OCCUPIED_ORDER_TABLE;

@Component
public class EatInOrderTableEmptyChecker implements EatInOrderTableChecker {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public EatInOrderTableEmptyChecker(EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    public UUID validate(UUID orderTableId) {
        EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
        if (eatInOrderTable.isEmpty()) {
            throw new IllegalStateException(NOT_OCCUPIED_ORDER_TABLE);
        }
        return orderTableId;
    }

}
