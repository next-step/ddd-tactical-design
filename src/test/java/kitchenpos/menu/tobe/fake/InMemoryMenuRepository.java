package kitchenpos.menu.tobe.fake;



import kitchenpos.menu.tobe.domain.menu.Menu;
import kitchenpos.menu.tobe.domain.menu.MenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, Menu> store = new HashMap<>();

    @Override
    public Menu save(Menu menu) {

        store.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return store.values().stream()
                .filter(menu -> ids.contains(menu.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        return store.values().stream()
                .filter(menu -> menu.getMenuProducts().stream()
                        .anyMatch(mp -> mp.getProductId().equals(productId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(store.values());
    }
}
