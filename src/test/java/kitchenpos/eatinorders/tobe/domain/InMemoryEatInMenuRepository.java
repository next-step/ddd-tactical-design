package kitchenpos.eatinorders.tobe.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryEatInMenuRepository implements EatInMenuRepository {
    private final Map<UUID, EatInMenu> menus = new HashMap<>();

    @Override
    public EatInMenu save(final EatInMenu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }


    @Override
    public List<EatInMenu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
                .stream()
                .filter(menu -> ids.contains(menu.getId()))
                .collect(Collectors.toList());
    }

}
