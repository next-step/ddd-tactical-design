package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<EatInOrderId, EatInOrder> eatInOrders;

    public InMemoryEatInOrderRepository() {
        this(new HashMap<>());
    }

    public InMemoryEatInOrderRepository(final Map<EatInOrderId, EatInOrder> eatInOrders) {
        this.eatInOrders = eatInOrders;
    }

    @Override
    public EatInOrder save(final EatInOrder eatInOrder) {
        eatInOrders.put(eatInOrder.id(), eatInOrder);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(final EatInOrderId id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus eatInOrderStatus) {
        return eatInOrders.values().stream()
                .allMatch(order -> order.isSameOrderTable(orderTableId) && order.isSameStatus(eatInOrderStatus));
    }
}
