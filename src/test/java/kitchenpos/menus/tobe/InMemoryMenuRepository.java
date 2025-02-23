package kitchenpos.menus.tobe;

import java.util.*;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menus.put(menu.getIdValue(), menu);
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
                .filter(menu -> ids.contains(menu.getIdValue()))
                .toList();
    }

    @Override
    public List<Menu> findAllByProductId(final Long productId) {
        return menus.values()
                .stream()
                .filter(menu -> menu.hasProduct(productId))
                .toList();
    }
}
