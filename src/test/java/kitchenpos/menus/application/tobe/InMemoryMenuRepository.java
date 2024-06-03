package kitchenpos.menus.application.tobe;


import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
                .stream()
                .filter(menu -> ids.contains(menu.getId()))
                .toList();
    }

    @Override
    public List<Menu> findAllByProductId(final UUID productId) {
        return menus.values()
                .stream()
                .filter(menu -> menu.getMenuProducts().getMenuProducts().stream().anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
                .toList();
    }
}