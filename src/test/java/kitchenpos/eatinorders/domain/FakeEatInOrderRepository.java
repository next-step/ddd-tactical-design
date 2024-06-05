package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<EatInOrder> findAllByOrderTableId(UUID orderTableId) {
        return repository.values()
                .stream()
                .filter(thisOrder -> thisOrder.getOrderTableId().equals(orderTableId))
                .collect(Collectors.toList());
    }
}
