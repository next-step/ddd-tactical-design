package kitchenpos.tobe.eatinorders.application.ordertable;

import static kitchenpos.tobe.eatinorders.application.Fixtures.NOT_COMPLETED_ORDER_TABLE_ID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;

public class FakeEatInOrderRepository implements EatInOrderRepository {

    @Override
    public EatInOrder save(EatInOrder eatInOrder) {
        return null;
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<EatInOrder> findAll() {
        return null;
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, EatInOrderStatus status) {
        if (orderTableId.equals(NOT_COMPLETED_ORDER_TABLE_ID)) {
            return true;
        }
        return false;
    }
}
