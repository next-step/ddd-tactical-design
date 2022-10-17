package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.stereotype.Component;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException.CANT_CLEAR;
import static kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus.*;

@Component
public class TablePolicy {
    private final EatInOrderRepository eatInOrderRepository;

    public TablePolicy(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public void validateClearable(OrderTable orderTable) {
        if (eatInOrderRepository.existsByOrderTableAndOrderStatusNot(orderTable, COMPLETED)) {
            throw new IllegalOrderTableStatusException(CANT_CLEAR);
        }
    }
}
