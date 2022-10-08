package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<Long, Menu> menus = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(final Long id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<Long> ids) {
        return menus.values()
                .stream()
                .filter(menu -> ids.contains(menu.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAllByProductId(final Long productId) {
        return menus.values()
                .stream()
                .filter(menu -> menu.getMenuProducts().stream().anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
                .collect(Collectors.toList());
    }
}
