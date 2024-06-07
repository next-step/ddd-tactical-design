package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.*;

public class FakeMenuRepository implements MenuRepository {
    private Map<UUID, Menu> repository = new HashMap<>();

    @Override
    public Menu save(Menu menu) {
        repository.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(repository.get(id));
    }
}
