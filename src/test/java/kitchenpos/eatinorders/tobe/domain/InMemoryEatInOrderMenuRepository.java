package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.DefaultEatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenu;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenuRepository;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryEatInOrderMenuRepository implements EatInOrderMenuRepository {

    private final Map<UUID, EatInOrderMenu> eatInOrderMenus;

    public InMemoryEatInOrderMenuRepository() {
        this(new HashMap<>());
    }

    public InMemoryEatInOrderMenuRepository(final Map<UUID, EatInOrderMenu> eatInOrderMenus) {
        this.eatInOrderMenus = eatInOrderMenus;
    }

    @Override
    public EatInOrderMenus findAllByIdIn(final List<UUID> eatInOrderMenuIds) {
        return new DefaultEatInOrderMenus(eatInOrderMenuIds.stream()
                .map(eatInOrderMenus::get)
                .toList());
    }
}
