package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryOrderMenuRepository implements OrderMenuRepository {

    private final Map<UUID, OrderMenu> orderMenuMap = new HashMap<>();

    @Override
    public OrderMenu save(final OrderMenu orderMenu) {
        orderMenuMap.put(orderMenu.getMenuId(), orderMenu);
        return orderMenu;
    }

    @Override
    public List<OrderMenu> findAllByIdIn(final List<UUID> ids) {
        return orderMenuMap.values()
                .stream()
                .filter(menu -> ids.contains(menu.getMenuId()))
                .collect(Collectors.toList());
    }
}
