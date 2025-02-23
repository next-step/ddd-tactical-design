package kitchenpos.menus.tobe.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<UUID, Menu> menus = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Menu save(final Menu menu) {
        menu.menuProducts()
                .forEach(it -> it.setSeq(sequence.incrementAndGet()));
        menus.put(menu.idValue(), menu);
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
                .filter(menu -> ids.contains(menu.idValue()))
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
