package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.todo.EatInOrder;

public interface SaveEatInOrderPort {
    EatInOrder save(EatInOrder order);
}
