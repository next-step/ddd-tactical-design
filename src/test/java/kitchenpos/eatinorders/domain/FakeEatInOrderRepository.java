package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeEatInOrderRepository implements EatInOrderRepository {
    private Map<UUID, EatInOrder> repository = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder order) {
        repository.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public boolean isAllCompleteByOrderTable(OrderTable orderTable) {
        return repository.values().stream()
                .allMatch(
                        eatInOrder -> eatInOrder.getOrderTableId().equals(orderTable.getId())
                        && eatInOrder.getStatus() == EatInOrderStatus.COMPLETED
                );
    }
}
