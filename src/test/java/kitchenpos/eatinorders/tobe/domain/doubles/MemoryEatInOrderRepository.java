package kitchenpos.eatinorders.tobe.domain.doubles;

import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus.COMPLETED;

public class MemoryEatInOrderRepository implements EatInOrderRepository {

    private final Map<UUID, EatInOrder> store = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder order) {
        UUID id = UUID.randomUUID();
        ReflectionTestUtils.setField(order, "id", id);
        store.put(id, order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return store.values()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findAny();
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean hasOngoingOrder(UUID tableId) {
        return store.values()
                .stream()
                .anyMatch(it -> it.orderTableEq(tableId) && it.statusNotEq(COMPLETED));
    }
}
