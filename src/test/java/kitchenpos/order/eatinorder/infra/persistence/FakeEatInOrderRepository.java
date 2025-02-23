package kitchenpos.order.eatinorder.infra.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;

public class FakeEatInOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> storage;

    public FakeEatInOrderRepository(Map<UUID, EatInOrder> storage) {
        this.storage = storage;
    }

    @Override
    public Optional<EatInOrder> findById(UUID eatInOrderId) {
        return Optional.of(storage.get(eatInOrderId));
    }

    @Override
    public EatInOrder save(EatInOrder eatInOrder) {
        UUID eatInOrderId = UUID.randomUUID();
        eatInOrder.setId(eatInOrderId);
        storage.put(eatInOrderId, eatInOrder);
        return eatInOrder;
    }

    @Override
    public boolean existsByOrderTableAndEatInOrderFlowNot(OrderTable orderTable, EatInOrderFlow eatInOrderFlow) {
        Optional<EatInOrder> eatInOrder = storage.values().stream()
                .filter(s -> s.getOrderTable() == orderTable)
                .findFirst();
        return eatInOrder
                .filter(flow -> flow.getEatInOrderFlow() != eatInOrderFlow)
                .isPresent();
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(storage.values());
    }
}
