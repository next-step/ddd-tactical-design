package kitchenpos.menu.fakerepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;

public class MenuGroupFakeRepository implements MenuGroupRepository {

    private final Map<UUID, MenuGroupNew> menuGroups = new HashMap<>();

    @Override
    public MenuGroupNew save(final MenuGroupNew entity) {
        menuGroups.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public Optional<MenuGroupNew> findById(final UUID id) {
        return Optional.ofNullable(menuGroups.get(id));
    }
}
