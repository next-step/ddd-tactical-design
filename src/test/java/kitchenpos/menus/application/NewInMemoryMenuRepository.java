package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.NewMenu;
import kitchenpos.menus.tobe.domain.MenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class NewInMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, NewMenu> menus = new HashMap<>();

    @Override
    public NewMenu save(final NewMenu newMenu) {
        menus.put(newMenu.getId(), newMenu);
        return newMenu;
    }

    @Override
    public Optional<NewMenu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<NewMenu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<NewMenu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
            .stream()
            .filter(menu -> ids.contains(menu.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<NewMenu> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(menu -> menu.getMenuProductList().stream().anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
            .collect(Collectors.toList());
    }
}
