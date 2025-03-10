package kitchenpos.mock.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;


public class FakeMenuRepository implements TobeMenuRepository {

    private final Map<UUID, Menu> dataMap = new ConcurrentHashMap<>();

    @Override
    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            throw new IllegalArgumentException("Menu id cannot be null");
        }
        UUID id = menu.getId();
        dataMap.put(id, menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return dataMap.values()
            .stream()
            .filter(menu -> menu.getId().equals(id))
            .findFirst();
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(dataMap.values());
    }

    @Override
    public int countAllByIdIn(List<UUID> ids) {
        return ids.stream()
            .map(dataMap::get)
            .filter(Objects::nonNull)
            .toList().size();
    }
}
