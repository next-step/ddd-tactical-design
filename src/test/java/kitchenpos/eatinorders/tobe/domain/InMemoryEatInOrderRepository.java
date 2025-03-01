package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
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
    public EatInOrder save(final EatInOrder order) {
        eatInOrders.put(order.getId(), order);
        return order;
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
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus orderStatus) {
        return eatInOrders.values().stream()
                .allMatch(order -> order.isSameOrderTable(orderTableId) && order.isSameStatus(orderStatus));
    }
}
