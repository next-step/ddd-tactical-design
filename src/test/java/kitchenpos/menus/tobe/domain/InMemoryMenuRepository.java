package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.MenuId;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<MenuId, Menu> menus = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Menu save(final Menu menu) {
        menu.menuProducts()
                .forEach(it -> it.setSeq(sequence.incrementAndGet()));
        menus.put(menu.id(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(final MenuId id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<MenuId> ids) {
        return menus.values()
                .stream()
                .filter(menu -> ids.contains(menu.id()))
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
