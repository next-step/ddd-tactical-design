package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ToBeInMemoryMenuRepository implements ToBeMenuRepository {
    private final Map<UUID, ToBeMenu> menus = new HashMap<>();

    @Override
    public ToBeMenu save(final ToBeMenu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<ToBeMenu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<ToBeMenu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<ToBeMenu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
            .stream()
            .filter(menu -> ids.contains(menu.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ToBeMenu> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(menu -> menu.getMenuProducts().stream().anyMatch(menuProduct -> menuProduct.getProduct().getId().equals(productId)))
            .collect(Collectors.toList());
    }
}
